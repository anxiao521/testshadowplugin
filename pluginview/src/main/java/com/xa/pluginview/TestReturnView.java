package com.xa.pluginview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xa.common.plugin.PluginViewEventInterface;
import com.xa.common.plugin.PluginViewInterface;

/**
 * @author : anxiao
 * date : 2022/8/16 13:55
 * description :
 */
public class TestReturnView implements PluginViewInterface {
    private Context context;
    public TestReturnView(Context context) {
        //可以做部分初始化
        this.context = context;
    }

    @Override
    public View getView(PluginViewEventInterface pluginViewEventInterface) {
        TextView textView=new TextView(context);
        textView.setText("我是插件view返回的view");
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
        textView.setBackgroundResource(R.drawable.bg_item_good);
        return textView;
    }
}
