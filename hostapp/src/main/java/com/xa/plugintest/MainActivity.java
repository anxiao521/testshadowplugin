package com.xa.plugintest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.xa.common.constant.PluginConstant;
import com.xa.plugintest.manager.pluginview.DynamicPluginViewManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv1).setOnClickListener(v -> {
            PluginHelper.getInstance().singlePool.execute(() -> {
                Bundle bundle = new Bundle();
                bundle.putString(PluginConstant.KEY_PLUGIN_ZIP_PATH, PluginHelper.getInstance().pluginZipFile.getAbsolutePath());
                bundle.putString(PluginConstant.KEY_PLUGIN_PART_KEY, "plugin1");
                bundle.putString(PluginConstant.KEY_ACTIVITY_CLASSNAME, "com.xa.plugin1.MainActivity");
                HostApplication.getApp().getPluginManager("plugin1",PluginHelper.getInstance().pluginManagerFile).enter(this, PluginConstant.FROM_ID_START_ACTIVITY, bundle, new EnterCallback() {
                    @Override
                    public void onShowLoadingView(View view) {

                    }

                    @Override
                    public void onCloseLoadingView() {

                    }

                    @Override
                    public void onEnterComplete() {

                    }
                });
            });
        });
        findViewById(R.id.tv2).setOnClickListener(v -> {
            PluginHelper.getInstance().singlePool.execute(() -> {
                Bundle bundle = new Bundle();
                bundle.putString(PluginConstant.KEY_PLUGIN_ZIP_PATH, PluginHelper.getInstance().pluginZipFile2.getAbsolutePath());
                bundle.putString(PluginConstant.KEY_PLUGIN_PART_KEY, "plugin2");
                bundle.putString(PluginConstant.KEY_ACTIVITY_CLASSNAME, "com.xa.plugin2.MainActivity");
                HostApplication.getApp().getPluginManager("plugin2",PluginHelper.getInstance().pluginManagerFile).enter(this, PluginConstant.FROM_ID_START_ACTIVITY, bundle, new EnterCallback() {
                    @Override
                    public void onShowLoadingView(View view) {

                    }

                    @Override
                    public void onCloseLoadingView() {

                    }

                    @Override
                    public void onEnterComplete() {

                    }
                });

            });
        });
        PluginHelper.getInstance().singlePool.execute(() -> {
            DynamicPluginViewManager dynamicPluginViewManager = new DynamicPluginViewManager(this, PluginHelper.getInstance().pluginViewFile);
            LinearLayout line = findViewById(R.id.line);
            line.post(() -> {
                line.addView(dynamicPluginViewManager.getView(null));
            });
        });

    }
}