package com.cypoem.retrofit;

import android.app.Application;
import android.content.Context;

import lotcom.zhpan.idea.utils.Utils;


/**
 * Created by zhpan on 2017/4/18.
 */

public class MApp extends Application {
    private static MApp MApp;
    public static Context getAppContext() {
        return MApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        MApp =this;
    }
}
