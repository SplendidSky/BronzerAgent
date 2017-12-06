package com.splendidsky.bronzeragent;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 伟宸 on 2017/12/5.
 */

public class RemoteCmdHandler {
    private static String TAG = "RemoteCmdHandler";

    @NonNull
    public static String handle(String cmd) {
        Log.d(TAG, "Handling cmd " + cmd);
        StringBuilder rntMsg = new StringBuilder();
        if(cmd.equalsIgnoreCase("list")) {
            List<AppInfo> appInfos = new ArrayList<>();
            appInfos = AppTool.getInstallApps();
            for (AppInfo appInfo : appInfos) {
                rntMsg.append(appInfo.getAppName()).append("\n");
            }
        }
        return rntMsg.toString();
    }
}
