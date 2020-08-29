package com.example.romi.helloword.s08cl05_minitwitter.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    //l)
    private static MyApp instance;

    public static MyApp getInstance(){
        return instance;
    }


    //m)
    public static Context getContext(){
        return instance;
    }

    //n)
    @Override
    public void onCreate(){
        instance = this;
        super.onCreate();
    }
}
