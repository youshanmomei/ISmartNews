package cn.qiuc.org.ismartnews.base.impl.menu;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.activity.MainUI;
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
    private List<TabDetailPager> tabPagers;

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
            tabPagers.add(new TabDetailPager(mContext, tabData.get(i)));
        }

        pager.setAdapter(new MyAdapter());

        indicator.setViewPager(pager);

        indicator.setOnPageChangeListener(new MyOnChangeListener());

    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return tabData.get(position).title;
        }

        @Override
        public int getCount() {
            return tabData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //get the page to sign the corresponding details view
            TabDetailPager detailPager = tabPagers.get(position);
            container.addView(detailPager.rootView);

            detailPager.initData();
            return detailPager.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class MyOnChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MainUI mainUI = (MainUI) mContext;
            SlidingMenu slidingMenu = mainUI.getSlidingMenu();

            if (position == 0) {
                slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            } else {
                slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
