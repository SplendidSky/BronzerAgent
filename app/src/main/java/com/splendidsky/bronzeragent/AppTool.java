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

/**
 * Get installed/allowBackup apps and exported activities/services/receivers/providers
 *
 * @author Weichen Chen
 */
public class AppTool {
    private static final String TAG = "AppTool";
    private static PackageManager mPackageManager = AppApplication.getInstance().getPackageManager();


    /**
     * Get installed apps
     *
     * @return a List that consists of installed apps
     */
    public static List<AppInfo> getInstallApps() {
        Log.d(TAG, "getInstallApps");
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        try {
            List<PackageInfo> packageInfos = mPackageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                // 过滤掉系统app
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

    /**
     * Check if an app is allowed backup
     *
     * @param packageName a string
     * @return a boolean
     * @exception PackageManager.NameNotFoundException
     */
    public static boolean isAllowBackup(String packageName) throws PackageManager.NameNotFoundException {
        Log.d(TAG, "isAllowBackup");
        ApplicationInfo applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
        if ((applicationInfo.flags & ApplicationInfo.FLAG_ALLOW_BACKUP) != 0)
            return true;
        return false;
    }

    /**
     * Get activities of an app
     *
     * @param packageName a string
     * @return a List that consists of activities
     * @exception PackageManager.NameNotFoundException
     */
    public static List<ActivityInfo> getActivityInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ActivityInfo> activityInfos = new ArrayList<>();
        PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        if (packageInfo.activities != null) {
            activityInfos.addAll(Arrays.asList(packageInfo.activities));
        }
        return activityInfos;
    }

    /**
     * Get services of an app
     *
     * @param packageName a string
     * @return a List that consists of services
     * @exception PackageManager.NameNotFoundException
     */
    public static List<ServiceInfo> getServiceInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ServiceInfo> serviceInfos = new ArrayList<>();
        PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_SERVICES);
        if (packageInfo.services != null) {
            serviceInfos.addAll(Arrays.asList(packageInfo.services));
        }
        return serviceInfos;
    }

    /**
     * Get receivers of an app
     *
     * @param packageName a string
     * @return a List that consists of receivers
     * @exception PackageManager.NameNotFoundException
     */
    public static List<ActivityInfo> getReceiverInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ActivityInfo> receiverInfos = new ArrayList<>();
        PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_RECEIVERS);
        if (packageInfo.receivers != null) {
            receiverInfos.addAll(Arrays.asList(packageInfo.receivers));
        }
        return receiverInfos;
    }

    /**
     * Get providers of an app
     *
     * @param packageName a string
     * @return a List that consists of providers
     * @exception PackageManager.NameNotFoundException
     */
    public static List<ProviderInfo> getProviderInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ProviderInfo> providerInfos = new ArrayList<>();
        PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_PROVIDERS);
        if (packageInfo.providers != null) {
            providerInfos.addAll(Arrays.asList(packageInfo.providers));
        }
        return providerInfos;
    }

    /**
     * Get exported components of an app
     *
     * @param componentInfos a List
     * @return a List that consists of exported components
     */
    @SuppressWarnings("unchecked")
    public static List<?> pickExportedComponentInfos(List<?> componentInfos) {
        List<ComponentInfo> exportComponentInfos = new ArrayList<>();
        for (ComponentInfo componentInfo : (List<ComponentInfo>)componentInfos) {
            if (componentInfo.exported) {
                exportComponentInfos.add(componentInfo);
            }
        }
        return exportComponentInfos;
    }

    /**
     * Get exported components of an app
     *
     * @param packageName a String
     * @return a List that consists of exported components
     */
    @SuppressWarnings("unchecked")
    public static List<ActivityInfo> getExportedActivityInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ActivityInfo> activityInfos = getActivityInfos(packageName);
        return (List<ActivityInfo>) pickExportedComponentInfos(new ArrayList<ComponentInfo>(activityInfos));
    }

    /**
     * Get exported services of an app
     *
     * @param packageName a String
     * @return a List that consists of exported services
     */
    @SuppressWarnings("unchecked")
    public static List<ServiceInfo> getExportedServiceInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ServiceInfo> serviceInfos = getServiceInfos(packageName);
        return (List<ServiceInfo>) pickExportedComponentInfos(new ArrayList<ComponentInfo>(serviceInfos));
    }

    /**
     * Get exported receivers of an app
     *
     * @param packageName a String
     * @return a List that consists of exported receivers
     */
    @SuppressWarnings("unchecked")
    public static List<ActivityInfo> getExportedReceiverInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ActivityInfo> receiverInfos = getReceiverInfos(packageName);
        return (List<ActivityInfo>) pickExportedComponentInfos(new ArrayList<ComponentInfo>(receiverInfos));
    }

    /**
     * Get exported providers of an app
     *
     * @param packageName a String
     * @return a List that consists of exported providers
     */
    @SuppressWarnings("unchecked")
    public static List<ProviderInfo> getExportedProviderInfos(String packageName) throws PackageManager.NameNotFoundException {
        List<ProviderInfo> providerInfos = getProviderInfos(packageName);
        return (List<ProviderInfo>) pickExportedComponentInfos(new ArrayList<ComponentInfo>(providerInfos));
    }


}
