package cn.qiuc.org.ismartnews.base.impl.menu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.qiuc.org.ismartnews.base.MenuDetailBasePager;

/**
 * Created by admin on 2016/4/27.
 */
public class TopicDeatilPager extends MenuDetailBasePager {
    public TopicDeatilPager(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("专题详情");
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

}
