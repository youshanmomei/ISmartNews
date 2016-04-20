package cn.qiuc.org.ismartnews.base.impl;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.qiuc.org.ismartnews.base.BasePager;
import cn.qiuc.org.ismartnews.util.LogUtil;

/**
 * Created by admin on 2016/4/20.
 */
public class SettingPager extends BasePager {
    private final String TAG = "SettingPager";

    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        LogUtil.d(TAG, "设置 加载了");
        ib_basepager_menu.setVisibility(View.INVISIBLE);
        tv_basepager_title.setText("设置");

        TextView textView = new TextView(mContext);
        textView.setText("设置");
        textView.setGravity(Gravity.CENTER);
        fl_basepager_content.removeAllViews();
        fl_basepager_content.addView(textView);
    }
}
