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

package com.xa.plugintest;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PluginHelper {

    /**
     * 动态加载的插件管理apk
     */
    public final static String sPluginManagerName = "manager1-debug.apk";

    /**
     * 动态加载的插件包，里面包含以下几个部分，插件apk，插件框架apk（loader apk和runtime apk）, apk信息配置关系json文件
     */
    public final static String sPluginZip =  "plugin-debug-1.zip" ;
    public final static String sPluginZip2 = "plugin-debug-2.zip";

    public final static String sPluginview = "pluginview-debug.apk";


    public File pluginManagerFile;

    public File pluginViewFile;

    public File pluginZipFile;
    public File pluginZipFile2;

    public ExecutorService singlePool = Executors.newSingleThreadExecutor();

    private Context mContext;

    private static PluginHelper sInstance = new PluginHelper();

    public static PluginHelper getInstance() {
        return sInstance;
    }

    private PluginHelper() {
    }

    public void init(Context context) {
        pluginManagerFile = new File(context.getFilesDir(), sPluginManagerName);

        pluginViewFile =new File(context.getFilesDir(), sPluginview);

        pluginZipFile = new File(context.getFilesDir(), sPluginZip);
        pluginZipFile2 = new File(context.getFilesDir(), sPluginZip2);

        mContext = context.getApplicationContext();

        singlePool.execute(new Runnable() {
            @Override
            public void run() {
                preparePlugin();
            }
        });

    }

    private void preparePlugin() {
        try {
            InputStream is = mContext.getAssets().open(sPluginManagerName);
            FileUtils.copyInputStreamToFile(is, pluginManagerFile);

            InputStream is2 = mContext.getAssets().open(sPluginview);
            FileUtils.copyInputStreamToFile(is2, pluginViewFile);

            InputStream zip = mContext.getAssets().open(sPluginZip);
            FileUtils.copyInputStreamToFile(zip, pluginZipFile);

            InputStream zip2 = mContext.getAssets().open(sPluginZip2);
            FileUtils.copyInputStreamToFile(zip2, pluginZipFile2);
            Log.e("TAG", "模拟所有插件下载完成插件下载完成" );
//            Toast.makeText(mContext,"",)
        } catch (IOException e) {
            Log.e("preparePlugin", e.getMessage() );
            throw new RuntimeException("从assets中复制apk出错", e);
        }
    }


}
