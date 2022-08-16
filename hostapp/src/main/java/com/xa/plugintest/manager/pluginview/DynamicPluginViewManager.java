package com.xa.plugintest.manager.pluginview;

import android.content.Context;
import android.view.View;


import com.xa.common.plugin.PluginViewEventInterface;
import com.xa.common.plugin.PluginViewInterface;

import java.io.File;

/**
 * @author : anxiao
 * date : 2022/7/18 9:33
 * description :apkview的管理类
 */
public class DynamicPluginViewManager implements PluginViewInterface {
    ViewImplLoader viewImplLoader;
    PluginViewInterface pluginViewInterface;
    public DynamicPluginViewManager (Context context, File latestImplApk){
        viewImplLoader=new ViewImplLoader(context,latestImplApk);
        pluginViewInterface= viewImplLoader.load();
    }

    @Override
    public View getView(PluginViewEventInterface pluginViewEventInterface) {
        return pluginViewInterface.getView(pluginViewEventInterface);
    }
}
