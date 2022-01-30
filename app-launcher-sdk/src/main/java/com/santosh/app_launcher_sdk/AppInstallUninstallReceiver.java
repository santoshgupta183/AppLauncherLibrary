package com.santosh.app_launcher_sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppInstallUninstallReceiver extends BroadcastReceiver {
    private LauncherSDK launcherSDK;

    public AppInstallUninstallReceiver(LauncherSDK launcherSDK) {
        this.launcherSDK = launcherSDK;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case Intent.ACTION_PACKAGE_ADDED:
                launcherSDK.notifyAppInstalled(intent);
                break;
            case Intent.ACTION_PACKAGE_REMOVED:
                launcherSDK.notifyAppUnInstalled(intent);
                break;
        }
    }
}
