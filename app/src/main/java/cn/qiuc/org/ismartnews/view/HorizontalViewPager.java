package cn.qiuc.org.ismartnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by admin on 2016/4/24.
 */
public class HorizontalViewPager extends ViewPager {

    private int downX;
    private int downY;

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * getParent().requestDisallowInterceptTouchEvent(true);
     * true， handle it itself
     * false, parent container handing
     *
     * 1.up and down, parent handle
     * 2.left to right
     * 2.1 in page 0, from left to right, itself do not handle it
     * 2.2 in the last page, from right to left, itself do not handle it
     * 2.3 else all, handle it itself
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();

                //when down, parent container do not intercept event
                //the parent container will pass on the event
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int diffX = moveX-downX;
                int diffY = moveY - downY;

                //left and right sliding
                if (Math.abs(diffY) < Math.abs(diffX)) {
                    if (getCurrentItem() == 0 && diffX > 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (getCurrentItem() == getAdapter().getCount() - 1 && diffX < 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                //up and down sliding
                else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                default:
                    break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
