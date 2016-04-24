package cn.qiuc.org.ismartnews.base.impl.menu;

import android.content.Context;
import android.view.View;

import com.lidroid.xutils.ViewUtils;

import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.base.MenuDetailBasePager;
import cn.qiuc.org.ismartnews.bean.NewsCenterBean;
import cn.qiuc.org.ismartnews.domain.TabDetailBean;
import cn.qiuc.org.ismartnews.util.BitmapHelper;

/**
 * Created by admin on 2016/4/23.
 */
public class TabDetailPager extends MenuDetailBasePager {
    private final NewsCenterBean.NewsTab tabData;
    private List<TabDetailBean.Data.TopNews> topnewsData;

    public TabDetailPager(Context context, NewsCenterBean.NewsTab newsTab) {
        super(context);
        this.tabData = newsTab;
        BitmapHelper.getBitmapUtils(mContext);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.tabdetail, null);
        ViewUtils.inject(this, view);

        //load carousel figure layout in the top
        View topnews = View.inflate(mContext, R.layout.topnews, null);
        ViewUtils.inject(this, topnews);

        //TODO ...


        return null;
    }
}
