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

package com.xa.xatestloader;

import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.tencent.shadow.core.loader.infos.ContainerProviderInfo;
import com.tencent.shadow.core.loader.managers.ComponentManager;


public class SampleComponentManager extends ComponentManager {

    /**
     * dynamic-runtime-apk 模块中定义的壳子Activity，需要在宿主AndroidManifest.xml注册
     */
    private static final String DEFAULT_ACTIVITY = "com.xa.xa_runtime.PluginDefaultProxyActivity";
    private static final String SINGLE_INSTANCE_ACTIVITY = "com.xa.xa_runtime.PluginSingleInstance1ProxyActivity";
    private static final String SINGLE_TASK_ACTIVITY = "com.xa.xa_runtime.PluginSingleTask1ProxyActivity";

    private Context context;

    public SampleComponentManager(Context context) {
        this.context = context;
    }


    /**
     * 配置插件Activity 到 壳子Activity的对应关系
     *
     * @param pluginActivity 插件Activity
     * @return 壳子Activity
     */
    @Override
    public ComponentName onBindContainerActivity(ComponentName pluginActivity) {
        //参数是全路径，主要对应非默认开启模式的activity，singletask需要单独对应activity
        //pluginActivity.getClassName()==com.xa.plugin1.MainActivity
        switch (pluginActivity.getClassName()) {
            /**
             * 这里配置对应的对应关系
             */
        }
        return new ComponentName(context, DEFAULT_ACTIVITY);
    }

    /**
     * 配置对应宿主中预注册的壳子contentProvider的信息
     */
    @Override
    public ContainerProviderInfo onBindContainerContentProvider(ComponentName pluginContentProvider) {
        return new ContainerProviderInfo(
                "com.tencent.shadow.core.runtime.container.PluginContainerContentProvider",
                context.getPackageName() + ".contentprovider.authority.dynamic");
    }

}
