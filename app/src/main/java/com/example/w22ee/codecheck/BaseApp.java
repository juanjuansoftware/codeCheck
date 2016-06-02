package com.example.w22ee.codecheck;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by lixi on 16/6/2.
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "900032221", false);
    }
}
