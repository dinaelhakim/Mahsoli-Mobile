package com.example.lenovo.testslidenerd;

import android.app.Application;

/**
 * Created by Enas on 07/05/2016.
 */


public class MyApplication extends Application {
    private static MyApplication MyApplication;

    public MyApplication getInstance(){
        return MyApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;
    }
}

