package com.ad.linsn.linsnandroids;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/*检测app是否在前台运行的代码不准确，需要重新编写*/


public class AppMonitor {
    private String PackageName = null;
    private Context context;

    public AppMonitor(Context context,String packageName){
        this.PackageName = packageName;
        this.context = context;
    };

    public void appMonitorStart() {
//        Log.e("MainActivity","isAppRunning start");
        if(!checkPackage())
            return ;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list.size() <= 0) {
            return ;
        }
        for (ActivityManager.RunningAppProcessInfo info : list) {
            if (info.processName.equals(PackageName)) {
                return;
            }
        }

        restartApp();
    }

    public void restartApp(){

//        Log.e("MainActivity","restartApp");
        if(PackageName.isEmpty())
            return;

        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(PackageName);
        context.startActivity(intent);
    }

    private boolean checkPackage(){
//        Log.e("MainActivity","checkPackage start");
        PackageInfo packageInfo = null;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(PackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }

        if(packageInfo == null)
            return false;

        return true;
    }

}
