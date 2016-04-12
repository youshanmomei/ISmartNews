package cn.qiuc.org.ismartnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.util.SharedPreferencesUtil;

public class GuideUI extends AppCompatActivity implements View.OnClickListener {

    private List<ImageView> images;
    private LinearLayout ll_guide_points;
    private ImageView iv_guide_redPoint;
    private Button bt_guide_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //request to remove the title
        setContentView(R.layout.activity_guide_ui);

        init();
    }

    private void init() {
        ViewPager vp_guide_bg = (ViewPager) findViewById(R.id.vp_guide_bg);
        ll_guide_points = (LinearLayout) findViewById(R.id.ll_guide_points);
        iv_guide_redPoint = (ImageView) findViewById(R.id.iv_guide_redPoint);
        bt_guide_start = (Button) findViewById(R.id.bt_guide_start);
        bt_guide_start.setOnClickListener(this);

        initData();

        vp_guide_bg.setAdapter(new MyAdapter());

        vp_guide_bg.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initData() {
        int[] imgIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
        //prepare picture data for viewpager
        images = new ArrayList<>();
        for (int i = 0; i < imgIds.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundResource(imgIds[i]);
            images.add(imageView);

            //add grey point
            ImageView point = new ImageView(getApplicationContext());
            point.setBackgroundResource(R.drawable.guide_point_normal);
            int dp2px = dp2Px(10);
            //set width and height
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px, dp2px);
            //set mergins
            if (i != 0) {
                params.leftMargin = dp2px;
            }
            point.setLayoutParams(params);
            ll_guide_points.addView(point);
        }
    }

    private int dp2Px(int dp) {
        //px = dp * (density ratio); 0.75 / 1 / 1.5 / 2
        float density = getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MainUI.class));
        SharedPreferencesUtil.putBoolean(getApplicationContext(), MainActivity.IS_APP_FIRST_OPEN, false);
        finish();
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position));
            return images.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //position: the ratio of the screen to the finger on the ViewPager
            //the red dot moving distance =  position * (distance between gray points):
            int redPointX = (int) ((positionOffset + position) * dp2Px(20));
            //let the red dot move
            //modify leftMargin the red dot
            android.widget.RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_guide_redPoint.getLayoutParams();
            layoutParams.leftMargin = redPointX;
            iv_guide_redPoint.setLayoutParams(layoutParams);
        }

        @Override
        public void onPageSelected(int position) {
            //callback on a page in the election
            if (position == images.size() - 1) {
                bt_guide_start.setVisibility(View.VISIBLE);
            } else {
                bt_guide_start.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
