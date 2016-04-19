package cn.qiuc.org.ismartnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NscrollViewPager extends ViewPager {

    public NscrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //removal of viewPager come with the sliding function
        //oneself do not handle events
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //no longer intercept child control events
        return false;
    }
}
