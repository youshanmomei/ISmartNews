package cn.qiuc.org.ismartnews.base.impl.menu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.qiuc.org.ismartnews.base.MenuDetailBasePager;

/**
 * Created by admin on 2016/4/27.
 */
public class InterractDetailPager extends MenuDetailBasePager {
    public InterractDetailPager(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("互动详情");
        textView.setGravity(Gravity.CENTER);
        return null;
    }
}
