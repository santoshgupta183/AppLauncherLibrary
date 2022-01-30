package com.santosh.app_launcher_sdk;

import android.graphics.drawable.Drawable;

public class InstalledApp {
    private String appName;
    private String packageName;
    private Drawable appIcon;
    private String mainActivityName;
    private String appVersionName;
    private int appVersionCode;

    public InstalledApp(String appName, String packageName, Drawable appIcon, String mainActivityName, String appVersionName, int appVersionCode) {
        this.appName = appName;
        this.packageName = packageName;
        this.appIcon = appIcon;
        this.mainActivityName = mainActivityName;
        this.appVersionName = appVersionName;
        this.appVersionCode = appVersionCode;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getMainActivityName() {
        return mainActivityName;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public int getAppVersionCode() {
        return appVersionCode;
    }

}
