package com.santosh.app_launcher_sdk;

public interface AppStatusChangeListener {
    void onAppInstalled(String packageName);
    void onAppUninstalled(String packageName);
}
