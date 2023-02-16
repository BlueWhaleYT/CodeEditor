package com.bluewhaleyt.codeeditor.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bluewhaleyt.codeeditor.App;
import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.common.SDKUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;

public class BaseActivity extends AppCompatActivity {

    public App app;

    private DynamicColorsUtil dynamicColors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        init();
    }

    @Override
    public void finish() {
        super.finish();
        IntentUtil.finishTransition(this);
    }

    private void init() {

        dynamicColors = new DynamicColorsUtil(this);

        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setToolBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);

        requestPermission();
    }

    private void requestPermission() {
        if (!PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            PermissionUtil.requestAllFileAccess(this);
        }
    }

}