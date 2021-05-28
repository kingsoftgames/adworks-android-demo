package com.adworks.android.demo;

/**
 * @Author : LZD
 * @Date on  : 5/21/21
 * @Desc :
 */
import com.danikula.videocache.HttpUrlSource;
import com.danikula.videocache.ProxyCacheException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 预加载/缓存工具类
 * <p>
 * <模拟客户端向代理服务器请求多媒体文件>
 *
 * @author : liuxs
 * @date : 2020/12/18
 */
public class PreCacher {

    private ExecutorService preCacheExecutor = Executors.newFixedThreadPool(8);

    private Map<String , PreCacheCallable> mCacheCallableMap;

    private PreCacher() {
        mCacheCallableMap = new HashMap<>();
    }

    public static PreCacher getInstance(){
        return InstanceHolder.mInstance;
    }

    /**
     * 开始缓存
     * @param url 缓存的地址
     * @param cacheBytes 缓存大小
     * @return
     */
    public synchronized boolean startCache(String url , int cacheBytes){
        String tmpurl = url;
        if(mCacheCallableMap.containsKey(tmpurl)){
            return false;
        }
        PreCacheCallable cacheCallable = new PreCacheCallable(tmpurl , cacheBytes);
        mCacheCallableMap.put(tmpurl , cacheCallable);
        Future<Boolean> cacheFuture =  preCacheExecutor.submit(cacheCallable);
        return true;
    }

    /**
     * 手动停止缓存
     * @param url
     */
    public synchronized void stopCache(String url){
        PreCacheCallable cacheCallable = mCacheCallableMap.get(url);
        if(cacheCallable != null){
            cacheCallable.stop();
            mCacheCallableMap.remove(url);
        }
    }

    /**
     * 结束所有缓存操作
     */
    public void stopAll(){
        for(PreCacheCallable cacheCallable : mCacheCallableMap.values()){
            cacheCallable.stop();
        }
        mCacheCallableMap.clear();
    }

    private boolean removePreCacheCallable(String url){
        PreCacheCallable cacheCallable = mCacheCallableMap.get(url);
        if(cacheCallable != null){
            mCacheCallableMap.remove(url);
            return true;
        }
        return false;
    }

    class PreCacheCallable implements Callable<Boolean>{

        String mUrl;                       //缓存地址
        int mCacheSize = Integer.MAX_VALUE;//准备缓存的大小，单位bytes
        boolean mStoped = false;                   //停止缓存
        private final Object mStopLock = new Object();
        HttpUrlSource mSource;

        public PreCacheCallable(String url , int cacheBytes) {
            mUrl = url;
            if(cacheBytes > 0){
                mCacheSize = cacheBytes;
            }
        }

        @Override
        public Boolean call() throws Exception {
            return connectToPoxyServer(mUrl);
        }


        public void stop(){
            synchronized (mStopLock){
                mStoped = true;
            }
        }

        private boolean isStoped(){
            return mStoped;
        }
        //TODO 需不需要考虑retry等
        private boolean connectToPoxyServer(String url){
            synchronized (mStopLock){
                if(isStoped()){
                    return false;
                }
            }
            mSource = new HttpUrlSource(url);
            try {
                mSource.open(0);
                byte[] buffer = new byte[8*1024];
                int readBytes;
                int offset = 0;
                while ((readBytes = mSource.read(buffer)) != -1) {
                    synchronized (mStopLock){
                        if(isStoped()){
                            break;
                        }
                    }
                    offset += readBytes;
                    if(offset >= mCacheSize){
                        break;
                    }
                }
            } catch (ProxyCacheException e) {
                e.printStackTrace();
                try {
                    mSource.close();
                } catch (ProxyCacheException proxyCacheException) {
                    proxyCacheException.printStackTrace();
                }
                return false;
            }finally {
                try {
                    mSource.close();
                    removePreCacheCallable(url);
                } catch (ProxyCacheException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    static class InstanceHolder{
        private static PreCacher mInstance = new PreCacher();
    }
}

