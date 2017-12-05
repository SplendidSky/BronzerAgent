package com.splendidsky.bronzeragent;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by 伟宸 on 2017/12/5.
 */

public class AppApplication extends Application {
    private static AppApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public static AppApplication getInstance() {
        return instance;
    }
}
