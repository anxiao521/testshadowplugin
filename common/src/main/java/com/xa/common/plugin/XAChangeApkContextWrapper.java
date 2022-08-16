/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xa.common.plugin;

import static android.content.pm.PackageManager.GET_META_DATA;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;

/**
 * 修改Context的apk路径的Wrapper。可将原Context的Resource和ClassLoader重新修改为新的Apk。
 */
public class XAChangeApkContextWrapper extends ContextWrapper {

    private Resources mResources;

    private LayoutInflater mLayoutInflater;

    final private ClassLoader mClassloader;

    /**
     * 宿主的真实对象
     */
    private Application mHostViewApplication;
    private Activity mHostViewActivity;

    public XAChangeApkContextWrapper(Context base, String apkPath, ClassLoader mClassloader) {
        super(base);
        this.mClassloader = mClassloader;
        mResources = createResources(apkPath, base);
    }

    private Resources createResources(String apkPath, Context base) {
        PackageManager packageManager = base.getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(apkPath, GET_META_DATA);
        packageArchiveInfo.applicationInfo.publicSourceDir = apkPath;
        packageArchiveInfo.applicationInfo.sourceDir = apkPath;
        try {
            return packageManager.getResourcesForApplication(packageArchiveInfo.applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public Application getmHostViewApplication() {
        return mHostViewApplication;
    }

    public void setmHostViewApplication(Application mHostViewApplication) {
        this.mHostViewApplication = mHostViewApplication;
    }

    public Activity getmHostViewActivity() {
        return mHostViewActivity;
    }

    public void setmHostViewActivity(Activity mHostViewActivity) {
        this.mHostViewActivity = mHostViewActivity;
    }

    @Override
    public Context getApplicationContext() {
        return this;
    }

    @Override
    public AssetManager getAssets() {
        return mResources.getAssets();
    }

    @Override
    public Resources getResources() {
        return mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        Log.e("getTheme: ", "获取设置的属性");
        return mResources.newTheme();
    }

    @Override
    public Object getSystemService(String name) {
        if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mLayoutInflater == null) {
                LayoutInflater layoutInflater = (LayoutInflater) super.getSystemService(name);
                mLayoutInflater = layoutInflater.cloneInContext(this);
            }
            return mLayoutInflater;
        }
        return super.getSystemService(name);
    }

    @Override
    public ClassLoader getClassLoader() {
        return mClassloader;
    }
}


