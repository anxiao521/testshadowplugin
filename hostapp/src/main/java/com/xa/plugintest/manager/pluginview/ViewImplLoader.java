package com.xa.plugintest.manager.pluginview;

import android.app.Application;
import android.content.Context;

import com.tencent.shadow.core.common.InstalledApk;
import com.tencent.shadow.dynamic.apk.ApkClassLoader;
import com.tencent.shadow.dynamic.apk.ImplLoader;
import com.xa.common.plugin.PluginViewFactory;
import com.xa.common.plugin.PluginViewInterface;
import com.xa.common.plugin.ProxyApplication;

import java.io.File;
import java.util.ServiceLoader;

/**
 * @author : anxiao
 * date : 2022/7/18 8:56
 * description :view的装载实现
 */
public class ViewImplLoader extends ImplLoader {
    /**
     * apk实现类的地址，插件了要固定死这个类名和报名
     */
    private static final String FACTORY_CLASS_NAME = "com.xa.dynamic.impl.PluginViewFactoryImpl";
    private static final String[] REMOTE_PLUGIN_MANAGER_INTERFACES = new String[]
            {
                    "com.tencent.shadow.core.common",
                    "com.tencent.shadow.dynamic.host",
            };
    final private Context applicationContext;
    final private Context mContext;
    final private InstalledApk installedApk;

    ViewImplLoader(Context context,File apk) {
        applicationContext = context.getApplicationContext();
        mContext=context;
        File root = new File(applicationContext.getFilesDir(), "HelloImplLoader");
        File odexDir = new File(root, Long.toString(apk.lastModified(), Character.MAX_RADIX));
        odexDir.mkdirs();
        installedApk = new InstalledApk(apk.getAbsolutePath(), odexDir.getAbsolutePath(), null);
    }

    PluginViewInterface load() {
        ApkClassLoader apkClassLoader = new ApkClassLoader(
                installedApk,
                getClass().getClassLoader(),
                loadWhiteList(installedApk,"com.xa.dynamic.impl.WhiteList","sWhiteList"),
                1
        );

        ProxyApplication contextForApi = new ProxyApplication((Application) applicationContext,
                mContext,
                installedApk.apkFilePath,
                apkClassLoader
        );
        try {
            PluginViewFactory factory = apkClassLoader.getInterface(
                    PluginViewFactory.class,
                    FACTORY_CLASS_NAME
            );
            return factory.build(contextForApi);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String[] getCustomWhiteList() {
        return REMOTE_PLUGIN_MANAGER_INTERFACES;
    }
}
