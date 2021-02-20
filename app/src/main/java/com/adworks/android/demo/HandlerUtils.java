package com.adworks.android.demo;

import android.os.Handler;
import android.os.Looper;

/**
 * @Author: Leo Gao
 * @Date: 2020/7/22
 */
public class HandlerUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void runOnUIThread(Runnable runnable) {
        handler.post(runnable);
    }

    public static void runOnUIThread(Runnable runnable,long delayTime) {
        handler.postDelayed(runnable,delayTime);
    }

}
