package com.xa.common.plugin;

import android.view.View;

/**
 * @author : anxiao
 * date : 2022/7/12 9:39
 * description :提供插件类的实现接口
 */
public interface PluginViewInterface {

    /**
     *
     * @param pluginViewEventInterface
     * @return 返回实现的view。
     */
    View getView(PluginViewEventInterface pluginViewEventInterface);
}
