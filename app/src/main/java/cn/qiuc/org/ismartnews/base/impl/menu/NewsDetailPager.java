package cn.qiuc.org.ismartnews.base.impl.menu;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.base.MenuDetailBasePager;
import cn.qiuc.org.ismartnews.bean.NewsCenterBean;

/**
 * Created by admin on 2016/4/22.
 */
public class NewsDetailPager extends MenuDetailBasePager {
    /**page check data**/
    private List<NewsCenterBean.NewsTab> tabData;
    private ViewPager pager;
    private TabPageIndicator indicator;
    private List<TabPageIndicator> tabPagers;

    public NewsDetailPager(Context context, List<NewsCenterBean.NewsTab> children) {
        super(context);
        this.tabData = children;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.newsdetail, null);
        pager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);

        return view;
    }

    @Override
    public void initData() {
        //create page which check detail
        tabPagers = new ArrayList<>();
        for (int i = 0; i < tabData.size(); i++) {
//            tabPagers.add(new TabDetailPager(mContext, tabData.get(i)));TODO
        }

//        pager.setAdapter(new MyAdapter());TODO

        indicator.setViewPager(pager);

//        indicator.setOnPageChangeListener(new MyOnChangeListener());TODO

    }
}
