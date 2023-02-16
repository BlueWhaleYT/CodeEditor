package com.bluewhaleyt.codeeditor;

import android.app.Application;
import android.content.Context;

import com.bluewhaleyt.common.DynamicColorsUtil;

public class App extends Application {

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DynamicColorsUtil.setDynamicColorsIfAvailable(this);
    }

    public static Context getContext() {
        return context;
    }
}
