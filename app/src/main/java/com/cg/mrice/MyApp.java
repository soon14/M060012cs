package com.cg.mrice;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by app on 2018/4/8.
 */
public class MyApp extends Application {

    private static MyApp myApplication;

    public static MyApp getInstance() {
        if (myApplication == null) {
            synchronized (MyApp.class) {
                if (myApplication == null) {
                    myApplication = new MyApp();
                }
            }
        }
        return myApplication;

    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPusht
    }
}
