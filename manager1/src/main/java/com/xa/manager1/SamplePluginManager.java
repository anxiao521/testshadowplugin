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

package com.xa.manager1;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.tencent.shadow.core.manager.installplugin.InstalledPlugin;
import com.tencent.shadow.dynamic.host.EnterCallback;
import com.xa.common.constant.PluginConstant;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SamplePluginManager extends FastPluginManager {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Context mCurrentContext;
    private String mPluginKey;

    public SamplePluginManager(Context context) {
        super(context);
        mCurrentContext = context;
    }


    /**
     * @return PluginManager实现的别名，用于区分不同PluginManager实现的数据存储路径
     */
    @Override
    protected String getName() {
        return "test-dynamic-manager";
    }

    /**
     * @return 宿主中注册的PluginProcessService实现的类名
     */
    @Override
    protected String getPluginProcessServiceName(String partKey) {
//        return "com.tj.host_app.PluginProcessPPS";
//        Log.e("plmple", "进程选择");
        if ("plugin1".equals(partKey)) {
            return "com.xa.plugintest.PluginProcessPPS";
        } else if ("plugin2".equals(partKey)) {
            return "com.xa.plugintest.PluginProcessPPS2";
        }else {
//            如果有默认PPS，可用return代替throw
            throw new IllegalArgumentException("unexpected plugin load request: " + partKey);
        }
    }

    /**
     * @param context  context
     * @param fromId   标识本次请求的来源位置，用于区分入口
     * @param bundle   参数列表
     * @param callback 用于从PluginManager实现中返回View
     */
    @Override
    public void enter(final Context context, long fromId, Bundle bundle, final EnterCallback callback) {
        Log.e("plmple", "管理app成功");
        mPluginKey = bundle.getString(PluginConstant.KEY_PLUGIN_PART_KEY);
        if (fromId == PluginConstant.FROM_ID_NOOP) {
            //do nothing.
        } else if (fromId == PluginConstant.FROM_ID_START_ACTIVITY) {
            onStartActivity(context, bundle, callback);
        } else if (fromId == PluginConstant.FROM_ID_CLOSE) {
            close();
        } else if (fromId == PluginConstant.FROM_ID_LOAD_VIEW_TO_HOST) {
        } else {
            throw new IllegalArgumentException("不认识的fromId==" + fromId);
        }

    }





    private void onStartActivity(final Context context, Bundle bundle, final EnterCallback callback) {
        final String pluginZipPath = bundle.getString(PluginConstant.KEY_PLUGIN_ZIP_PATH);
        Log.e("文件地址 ", pluginZipPath);
        final String partKey = bundle.getString(PluginConstant.KEY_PLUGIN_PART_KEY);
        final String className = bundle.getString(PluginConstant.KEY_ACTIVITY_CLASSNAME);
        if (className == null) {
            throw new NullPointerException("className == null");
        }
        final Bundle extras = bundle.getBundle(PluginConstant.KEY_EXTRAS);


        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InstalledPlugin installedPlugin = installPlugin(pluginZipPath, null, true);
                    loadPlugin(installedPlugin.UUID, partKey);
                    callApplicationOnCreate(partKey);
                    Intent pluginIntent = new Intent();
                    pluginIntent.setClassName(
                            context.getPackageName(),
                            className
                    );

                    if (extras != null) {
                        pluginIntent.replaceExtras(extras);
                    }
                    Intent intent = mPluginLoader.convertActivityIntent(pluginIntent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mPluginLoader.startActivityInPluginProcess(intent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (callback != null) {
                    callback.onCloseLoadingView();
                }
            }
        });
    }
}
