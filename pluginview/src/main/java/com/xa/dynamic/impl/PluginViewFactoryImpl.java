package com.xa.dynamic.impl;

import android.content.Context;

import com.xa.common.plugin.PluginViewFactory;
import com.xa.common.plugin.PluginViewInterface;
import com.xa.pluginview.TestReturnView;


/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/9/6
 * @description 此类包名及类名固定 格式查看ViewImplLoader的FACTORY_CLASS_NAME的值
 * @usage 返回接口实现类
 */
public final class PluginViewFactoryImpl implements PluginViewFactory {

    @Override
    public PluginViewInterface build(Context context) {
        //返回具体实现类
        return new TestReturnView(context);
    }
}
