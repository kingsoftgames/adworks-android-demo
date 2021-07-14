package com.adworks.android.demo;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * @Author : liuzhida
 * @Date on : 2020/8/4
 */
public class ToaUtils {

    public static void toastShort(Activity context, String msg) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
