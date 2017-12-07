package com.splendidsky.bronzeragent;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 伟宸 on 2017/11/24.
 */

public class AppTool {
    public static final String TAG = "AppTool";
    private static PackageManager mPackageManager = AppApplication.getInstance().getPackageManager();

    public static List<AppInfo> getInstallApps() {
        Log.d(TAG, "getInstallApps");
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        try {
            List<PackageInfo> packageInfos = mPackageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
//              if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
//                continue;
//              }
                AppInfo appInfo = new AppInfo();
                appInfo.setAppName(packageInfo.packageName);
                if (packageInfo.applicationInfo.loadIcon(mPackageManager) == null) {
                    continue;
                }
                appInfo.setImage(packageInfo.applicationInfo.loadIcon(mPackageManager));
                appInfos.add(appInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appInfos;
    }

    public static boolean isAllowBackup(String packageName) throws PackageManager.NameNotFoundException {
        Log.d(TAG, "isAllowBackup");
        ApplicationInfo applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
        if ((applicationInfo.flags & ApplicationInfo.FLAG_ALLOW_BACKUP) != 0)
            return true;
        return false;
    }

    public static List<ActivityInfo> getActivityInfos(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        return new ArrayList<>(Arrays.asList(packageInfo.activities));
    }

    public static List<ServiceInfo> getServiceInfos(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_SERVICES);
        return new ArrayList<>(Arrays.asList(packageInfo.services));
    }

    public static List<ActivityInfo> getReceiverInfos(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_RECEIVERS);
        return new ArrayList<>(Arrays.asList(packageInfo.receivers));
    }

    public static List<ProviderInfo> getProviderInfos(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_PROVIDERS);
        return new ArrayList<>(Arrays.asList(packageInfo.providers));
    }

    @SuppressWarnings("unchecked")
    public static List<?> pickExportComponentInfos(List<?> componentInfos) {
        List<ComponentInfo> exportComponentInfos = new ArrayList<>();
        for (ComponentInfo componentInfo : (List<ComponentInfo>)componentInfos) {
            if (componentInfo.exported) {
                exportComponentInfos.add(componentInfo);
            }
        }
        return exportComponentInfos;
    }

    @SuppressWarnings("unchecked")
    public static List<ActivityInfo> getExportActivityInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ActivityInfo> activityInfos = getActivityInfos(packageName);
        return (List<ActivityInfo>) pickExportComponentInfos(new ArrayList<ComponentInfo>(activityInfos));
    }

    @SuppressWarnings("unchecked")
    public static List<ServiceInfo> getExportServiceInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ServiceInfo> serviceInfos = getServiceInfos(packageName);
        return (List<ServiceInfo>) pickExportComponentInfos(new ArrayList<ComponentInfo>(serviceInfos));
    }

    @SuppressWarnings("unchecked")
    public static List<ActivityInfo> getExportReceiverInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ActivityInfo> receiverInfos = getReceiverInfos(packageName);
        return (List<ActivityInfo>) pickExportComponentInfos(new ArrayList<ComponentInfo>(receiverInfos));
    }

    @SuppressWarnings("unchecked")
    public static List<ProviderInfo> getExportProviderInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ProviderInfo> providerInfos = getProviderInfos(packageName);
        return (List<ProviderInfo>) pickExportComponentInfos(new ArrayList<ComponentInfo>(providerInfos));
    }


}
