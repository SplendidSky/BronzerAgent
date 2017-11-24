package com.splendidsky.bronzeragent;

import android.graphics.drawable.Drawable;

/**
 * Created by 伟宸 on 2017/11/24.
 */

public class AppInfo {
    private String appName;
    private Drawable image;

    public AppInfo() {
    }

    public AppInfo(String appName, Drawable image) {
        this.appName = appName;
        this.image = image;

    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {

        return appName;
    }

    public Drawable getImage() {
        return image;
    }
}
