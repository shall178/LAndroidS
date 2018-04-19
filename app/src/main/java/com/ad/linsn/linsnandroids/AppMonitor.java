package com.ad.linsn.linsnandroids;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class AppMonitor {
    private String PackageName = null;
    private Context context;

    public AppMonitor(Context context,String packageName){
        this.PackageName = packageName;
        this.context = context;
    };

    public boolean isAppRunning() {
        boolean isAppRunning = false;
        if(!checkPackage())
            return false;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(PackageName)) {
                return true;
            }
        }
        return false;
    }

    public void restartApp(){
        if(PackageName.isEmpty())
            return;

        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(PackageName);
        context.startActivity(intent);
    }

    private boolean checkPackage(){
        PackageInfo packageInfo = null;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(PackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(packageInfo == null)
            return false;

        return true;
    }

}
