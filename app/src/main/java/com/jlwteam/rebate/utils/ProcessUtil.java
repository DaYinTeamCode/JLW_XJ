package com.jlwteam.rebate.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.androidex.util.TextUtil;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 大印 on 2020/3/30.
 */
public class ProcessUtil {
    public ProcessUtil() {
    }

    public static boolean destroy(Process process) {
        boolean result = false;

        try {
            if (process != null) {
                process.destroy();
            }

            result = true;
        } catch (Exception var3) {

        }

        return result;
    }

    public static boolean isMainProcess(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService("activity");
            if (am == null) {
                return false;
            }

            List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
            if (processInfos == null || processInfos.size() == 0) {
                return false;
            }

            String mainProcessName = context.getPackageName();
            int myPid = android.os.Process.myPid();
            Iterator var5 = processInfos.iterator();

            while (var5.hasNext()) {
                ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) var5.next();
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
        } catch (Exception var7) {

        }

        return false;
    }

    public static String getProcessName(Context context) {
        return getProcessName(context, android.os.Process.myPid());
    }

    public static String getProcessName(Context context, int pid) {
        if (context == null) {
            return "";
        } else {
            try {
                ActivityManager am = (ActivityManager) context.getSystemService("activity");
                if (am == null) {
                    return "";
                }

                List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
                if (runningApps == null || runningApps.size() == 0) {
                    return "";
                }

                for (int i = 0; i < runningApps.size(); ++i) {
                    ActivityManager.RunningAppProcessInfo rapi = (ActivityManager.RunningAppProcessInfo) runningApps.get(i);
                    if (rapi.pid == pid) {
                        return TextUtil.filterNull(rapi.processName);
                    }
                }
            } catch (Exception var6) {

            }

            return "";
        }
    }
}
