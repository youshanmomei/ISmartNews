package cn.qiuc.org.ismartnews.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.activity.MainUI;
import cn.qiuc.org.ismartnews.base.BaseFragment;
import cn.qiuc.org.ismartnews.base.BasePager;
import cn.qiuc.org.ismartnews.base.impl.GovaffirsPager;
import cn.qiuc.org.ismartnews.base.impl.HomePager;
import cn.qiuc.org.ismartnews.base.impl.NewsCenterPager;
import cn.qiuc.org.ismartnews.base.impl.SettingPager;
import cn.qiuc.org.ismartnews.base.impl.SmartServicePager;

/**
 * Created by admin on 2016/4/14.
 */
public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.vp_content_pagers)
    private ViewPager vp_content_pagers;
    private List<BasePager> pagers;

    @ViewInject(R.id.rg_content_bottom)
    private RadioGroup rg_content_bottom;


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.content, null);

        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void initDate() {
        //update UI
        //prepare date
        List<BasePager> pagers = new ArrayList<>();
        pagers.add(new HomePager(mContext));
        pagers.add(new NewsCenterPager(mContext));
        pagers.add(new SmartServicePager(mContext));
        pagers.add(new GovaffirsPager(mContext));
        pagers.add(new SettingPager(mContext));

        vp_content_pagers.setAdapter(new MyAdapter());

        rg_content_bottom.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        vp_content_pagers.setOnPageChangeListener(new MyOnPageListener());

        pagers.get(0).initData();
        rg_content_bottom.check(R.id.rb_bottom_home);


    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return a layout based on position
            BasePager basePager = pagers.get(position);
            container.addView(basePager.rootView);

            return basePager.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //method for providing access to news center interface
    public NewsCenterPager getNewCenterPager() {
        return (NewsCenterPager)pagers.get(1);
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_bottom_home:
                    vp_content_pagers.setCurrentItem(0, false);//parameter 2 indicates whether the sliding effect
                    enableSlidingMenu(false);
                    break;
                case R.id.rb_bottom_newscenter:
                    vp_content_pagers.setCurrentItem(1, false);
                    enableSlidingMenu(true);
                    break;
                case R.id.rb_bottom_smartservice:
                    vp_content_pagers.setCurrentItem(2, false);
                    enableSlidingMenu(true);
                    break;
                case R.id.rb_bottom_govaffairs:
                    vp_content_pagers.setCurrentItem(3, false);
                    enableSlidingMenu(true);
                    break;
                case R.id.rb_bottom_setting:
                    vp_content_pagers.setCurrentItem(4, false);
                    enableSlidingMenu(false);
                    break;
                default:
                    break;
            }
        }
    }

    private void enableSlidingMenu(boolean enable) {
        MainUI mainUI = (MainUI) mContext;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    private class MyOnPageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}