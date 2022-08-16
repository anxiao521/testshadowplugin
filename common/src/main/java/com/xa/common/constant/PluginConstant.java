package com.xa.common.constant;

/**
 * @author : anxiao
 * date : 2022/8/11 17:37
 * description :
 */
public interface PluginConstant {
    /**
     * 插件安装地址
     */
    String KEY_PLUGIN_ZIP_PATH = "pluginZipPath";

    /**
     * 打开的view
     */
    String KEY_PLUGIN_VIEW_PATH = "KEY_PLUGIN_VIEW_PATH";

    /**
     * 打开的activity名称
     */
    String KEY_ACTIVITY_CLASSNAME = "KEY_ACTIVITY_CLASSNAME";
    /**
     * 额外字段
     */
    String KEY_EXTRAS = "KEY_EXTRAS";
    /**
     * 插件名称
     */
    String KEY_PLUGIN_PART_KEY = "KEY_PLUGIN_PART_KEY";
    /**
     * uuid
     */
    String KEY_PLUGIN_UUID = "KEY_PLUGIN_UUID";

    /**
     * UUID_NickName
     */
    String KEY_PLUGIN_UUID_NICKNAME = "KEY_PLUGIN_UUID_NICKNAME";


    String PART_KEY_PLUGIN_MAIN_APP = "plugin1";
    String PART_KEY_PLUGIN_ANOTHER_APP = "plugin1";

    int FROM_ID_NOOP = 1000;
    int FROM_ID_START_ACTIVITY = 1002;
    int FROM_ID_CLOSE = 1003;
    /**
     * 获取view
     */
    int FROM_ID_LOAD_VIEW_TO_HOST = 1004;
}
