package com.splendidsky.bronzeragent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 伟宸 on 2017/11/24.
 */

public class AppTool {
    public static final String TAG = "AppTool";

    public static List<AppInfo> getInstallApps(PackageManager packageManager) {
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
//              if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
//                continue;
//              }
                AppInfo appInfo = new AppInfo();
                appInfo.setAppName(packageInfo.packageName);
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                appInfo.setImage(packageInfo.applicationInfo.loadIcon(packageManager));
                appInfos.add(appInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appInfos;
    }
}
