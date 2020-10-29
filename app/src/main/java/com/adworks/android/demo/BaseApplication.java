package com.adworks.android.demo;

import android.app.Application;
import android.content.Context;

import xlfj.internal.dynamicloading.Mod;


public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        Mod.setAppkey("95271afb0cca7ef6b8244d9b9b4ce5e7ab5b318c");
//        Mod.run(this);
    }

}
