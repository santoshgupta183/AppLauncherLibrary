package com.santosh.app_launcher_sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LauncherSDK {

    private static LauncherSDK instance;
    private Context context;
    private AppInstallUninstallReceiver receiver;
    private AppStatusChangeListener appStatusChangeListener;

    private LauncherSDK(Context context) {
        this.context = context;
        this.receiver = new AppInstallUninstallReceiver(this);
    }

    public static LauncherSDK getInstance(Context context) {
        if (instance == null) {
            instance = new LauncherSDK(context);
        }
        return instance;
    }

    public List<InstalledApp> getAllInstalledApps(){
        List<InstalledApp> installedAppList = new ArrayList<>();

        PackageManager packageManager = context.getPackageManager();
        Intent main=new Intent(Intent.ACTION_MAIN, null);
        main.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> launchables=packageManager.queryIntentActivities(main, 0);

        for (ResolveInfo info : launchables) {
            PackageInfo packageInfo = null;
            try {
                packageInfo = packageManager.getPackageInfo(info.activityInfo.packageName, 0);
            } catch (PackageManager.NameNotFoundException e) { }

            installedAppList.add(new InstalledApp(info.loadLabel(packageManager).toString(),
                    info.activityInfo.packageName,
                    info.loadIcon(packageManager),
                    info.activityInfo.name,
                    packageInfo!=null?packageInfo.versionName:"NA",
                    packageInfo!=null?packageInfo.versionCode:0));
        }

        Collections.sort(installedAppList, getComparator());

        return installedAppList;
    }

    public void launchApp(InstalledApp app) {
        ComponentName name=new ComponentName(app.getPackageName(), app.getMainActivityName());
        Intent i=new Intent(Intent.ACTION_MAIN);

        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        i.setComponent(name);

        context.startActivity(i);
    }

    private Comparator<InstalledApp> getComparator() {
        return (app1, app2) -> app1.getAppName().compareTo(app2.getAppName());
    }

    public void notifyAppInstalled(Intent intent) {
        String packageName = null;
        if (intent != null && intent.getDataString() != null) {
            packageName = intent.getDataString();
        }
        appStatusChangeListener.onAppInstalled(packageName);
    }

    public void notifyAppUnInstalled(Intent intent) {
        String packageName = null;
        if (intent != null && intent.getDataString() != null) {
            packageName = intent.getDataString();
        }
        appStatusChangeListener.onAppUninstalled(packageName);
    }

    public void addAppStatusListener(AppStatusChangeListener appStatusChangeListener) {
        this.appStatusChangeListener = appStatusChangeListener;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");

        context.registerReceiver(receiver, intentFilter);
    }

    public void removeAppStatusListener(){
        context.unregisterReceiver(receiver);
    }
}
