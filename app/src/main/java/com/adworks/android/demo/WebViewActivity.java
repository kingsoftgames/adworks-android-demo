package com.adworks.android.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;

import java.io.File;

public class WebViewActivity extends AppCompatActivity {
    private String videoUrl = "https://ftp.shiyou.kingsoft.com/Publish/adworks/reward.mp4";
    private SuperExoPlayerView playerView;
    private IMediaPlayerExtendListener mIMediaPlayerExtendListener = new IMediaPlayerExtendListener() {
        @Override
        public void onPlayStart() {
            Log.d("lzd", "onPlayStart");
        }

        @Override
        public void onPlayEnd() {
            Log.d("lzd", "onPlayEnd");
        }

        @Override
        public void onPlayError(String msg) {
            Log.d("lzd", "onPlayError");
        }
    };
    private Button mButton;
    private CacheListener mCacheListener = new CacheListener() {
        @Override
        public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
            Log.d("lzd", "Aaaaaaaaaaaa=" + percentsAvailable);
            if (percentsAvailable == 100) {
                proxy.unregisterCacheListener(mCacheListener);
            }
        }
    };
    private HttpProxyCacheServer proxy;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        playerView = findViewById(R.id.video_view);
        mButton = findViewById(R.id.button2);
        proxy = App.getProxy(this);
        String proxyUrl = proxy.getProxyUrl(videoUrl);
        proxy.registerCacheListener(mCacheListener, videoUrl);
        VideoPreLoader videoPreLoader = new VideoPreLoader();
        if (!proxy.isCached(videoUrl)) {
            videoPreLoader.addPreloadURL(proxyUrl);

        }

        playerView.setExtendListener(mIMediaPlayerExtendListener);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerView.setVideoPath(proxyUrl);
                playerView.start();
            }
        });
    }

}