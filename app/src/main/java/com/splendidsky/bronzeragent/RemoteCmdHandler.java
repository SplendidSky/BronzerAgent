package com.splendidsky.bronzeragent;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

/**
 * Created by 伟宸 on 2017/12/5.
 */

/**
 * Handle cmds from PC
 *
 * @author Weichen Chen
 */
public class RemoteCmdHandler {
    private static String TAG = "RemoteCmdHandler";


    @NonNull
    public static String handle(String cmd) {
        Log.d(TAG, "Handling cmd " + cmd);
        StringBuilder rntMsg = new StringBuilder();

        String[] tokens = cmd.split(" ");
        String action = tokens[0];

        if(action.equalsIgnoreCase("list")) {
            List<AppInfo> appInfos = AppTool.getInstallApps();
            for (AppInfo appInfo : appInfos) {
                rntMsg.append(appInfo.getAppName()).append("\n");
            }
        }

        else if (action.equalsIgnoreCase("attacksurface")) {
            String packageName = "";
            try {
                packageName = tokens[1];
                List<ActivityInfo> exportActivityInfos = AppTool.getExportedActivityInfos(packageName);
                rntMsg.append("Exported activity:\n");
                for(ActivityInfo activityInfo : exportActivityInfos) {
                    rntMsg.append(activityInfo.name).append("\n");;
                }

                List<ServiceInfo> exportServiceInfos = AppTool.getExportedServiceInfos(packageName);
                rntMsg.append("Exported service:\n");
                for(ServiceInfo serviceInfo : exportServiceInfos) {
                    rntMsg.append(serviceInfo.name).append("\n");
                }

                List<ActivityInfo> exportReceiverInfos = AppTool.getExportedReceiverInfos(packageName);
                rntMsg.append("Exported receiver:\n");
                for(ActivityInfo receiverInfo : exportReceiverInfos) {
                    rntMsg.append(receiverInfo.name).append("\n");
                }

                List<ProviderInfo> exportProviderInfos = AppTool.getExportedProviderInfos(packageName);
                rntMsg.append("Exported provider:\n");
                for(ProviderInfo providerInfo : exportProviderInfos) {
                    rntMsg.append(providerInfo.name).append("\n");
                }
            } catch (PackageManager.NameNotFoundException e) {
                rntMsg.append("Package " + packageName + " not found").append("\n");
            }

        }

        Log.d(TAG, rntMsg.toString());
        return rntMsg.toString();
    }
}
