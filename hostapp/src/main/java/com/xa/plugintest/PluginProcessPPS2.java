package com.xa.plugintest;

import android.util.Log;

import com.tencent.shadow.dynamic.host.PluginProcessService;

/**
 * @author : anxiao
 * date : 2022/8/15 13:38
 * description :
 */
public class PluginProcessPPS2  extends PluginProcessService {
    public PluginProcessPPS2() {

    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.e("onCreate: ","服务pps2启动了" );
    }
}