package com.adworks.android.demo;

import android.content.Context;
import android.widget.Toast;

/**
 * @Author : liuzhida
 * @Date on : 2020/8/4
 */
public class ToaUtils {

    public static void toastShort(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
