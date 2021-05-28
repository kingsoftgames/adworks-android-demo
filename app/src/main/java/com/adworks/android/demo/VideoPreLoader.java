package com.adworks.android.demo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : LZD
 * @Date on  : 5/21/21
 * @Desc :
 */
public class VideoPreLoader {
    private Handler handler;
    private HandlerThread handlerThread;
    private List<String> cancelList = new ArrayList<>();

    public VideoPreLoader() {
        handlerThread = new HandlerThread("VideoPreLoaderThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    public void addPreloadURL(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                realPreload(data);
            }
        });
    }

    void cancelPreloadURLIfNeeded(String url) {
        cancelList.add(url);
    }

    void cancelAnyPreLoads() {
        handler.removeCallbacksAndMessages(null);
        cancelList.clear();
    }

    private void realPreload(String data) {
        if (data == null) {
            return;
        }
        HttpURLConnection conn = null;
        try {
            URL myURL = new URL(data);
            conn = (HttpURLConnection) myURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            byte[] buf = new byte[1024];
            int downLoadedSize = 0;
            do {
                int numRead = is.read(buf);
                downLoadedSize += numRead;
                if (numRead == -1) {
                    break;
                }
            } while (true);
            is.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

