package com.xa.common.plugin;/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class ProxyApplication extends XAChangeApkContextWrapper implements ComponentCallbacks2 {
    private static final String TAG = "Application";

    Application application;


    public ProxyApplication(Application application,Context base, String apkPath, ClassLoader mClassloader) {
        super(base,apkPath,mClassloader);
        this.application=application;
        setmHostViewApplication(application);
    }


    public void onCreate() {
        application.onCreate();
    }

    @Override
    public Context getApplicationContext() {
        return this;
    }

    public void onTerminate() {
        application.onTerminate();
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig) {
        application.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        application.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        application.onTrimMemory(level);
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        application.registerComponentCallbacks(callback);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        application.unregisterComponentCallbacks(callback);
    }

    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        application.registerActivityLifecycleCallbacks(callback);
    }

    public void unregisterActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        application.unregisterActivityLifecycleCallbacks(callback);
    }

    public void registerOnProvideAssistDataListener(Application.OnProvideAssistDataListener callback) {
        application.registerOnProvideAssistDataListener(callback);
    }

    public void unregisterOnProvideAssistDataListener(Application.OnProvideAssistDataListener callback) {
        application.unregisterOnProvideAssistDataListener(callback);
    }

    /**
     * Returns the name of the current process. A package's default process name
     * is the same as its package name. Non-default processes will look like
     * "$PACKAGE_NAME:$NAME", where $NAME corresponds to an android:process
     * attribute within AndroidManifest.xml.
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String getProcessName() {
        return Application.getProcessName();
    }

}
