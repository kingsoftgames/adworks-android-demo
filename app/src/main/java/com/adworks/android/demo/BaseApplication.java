package com.adworks.android.demo;

import android.app.Application;
import android.content.Context;

import xlfj.internal.dynamicloading.Mod;


public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Mod.run(this);
        Mod.setDebug(true);
    }

}
