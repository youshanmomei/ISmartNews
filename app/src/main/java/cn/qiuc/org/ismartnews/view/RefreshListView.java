package cn.qiuc.org.ismartnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.util.LogUtil;

/**
 * Created by admin on 2016/4/23.
 */
public class RefreshListView extends ListView {
    private final String TAG = "RefreshListView";

    private int headerHeight;
    private RotateAnimation up;
    private RotateAnimation down;
    private View footer;
    private int footerHeight;
    private OnRefreshListener mOnRefreshListener;
    private int downY;
    private View header;

    private static final int PULLDOWN_STATE = 0;
    private static final int RELEASE_STATE = 1;
    private static final int REFRESHING_STATE = 2;
    private int current_state = PULLDOWN_STATE;

    @ViewInject(R.id.iv_refresh_image)
    private ImageView iv_refresh_image;

    @ViewInject(R.id.pb_refresh_progress)
    private ProgressBar pb_refresh_progress;

    @ViewInject(R.id.tv_refresh_state)
    private TextView tv_refresh_state;

    @ViewInject(R.id.tv_refresh_time)
    private TextView tv_refresh_time;

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeadaer();
        initAnimation();
        initFoter();
    }

    private void initFoter() {
        footer = View.inflate(getContext(), R.layout.refresh_footer, null);

        //hide footer view
        footer.measure(0, 0);
        footerHeight = footer.getMeasuredHeight();
        footer.setPadding(0, -footerHeight, 0, 0);
        this.addFooterView(footer);

        this.setOnScrollListener(new MyOnScrollListener());
    }

    private void initAnimation() {
        up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        up.setDuration(500);
        up.setFillAfter(true);

        down = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        down.setDuration(500);
        down.setFillAfter(true);

    }

    private void initHeadaer() {
        header = View.inflate(getContext(), R.layout.refresh_header, null);
        ViewUtils.inject(this, header);

        //measure fist, get the measured height
        header.measure(0, 0);
        headerHeight = header.getMeasuredHeight();

        //hide head layout
        header.setPadding(0, -headerHeight, 0, 0);
        this.addHeaderView(header);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //slide your finger on a picture in the carousel
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //if refreshing, do not solve it
                if (current_state == REFRESHING_STATE) {
                    break;
                }

                //when the carousel figure display completely
                //the first visible item position is 0
                //handle itself event
                if (getFirstVisiblePosition() != 0) {
                    break;
                }

                if (downY == -1) {
                    downY = (int) ev.getY();
                }

                int moveY = (int) ev.getY();

                int diffY = moveY - downY;

                if (diffY > 0) {
                    int paddingTop = diffY - headerHeight;
                    //if paddingTop is 0, means head layout display completed
                    //set state
                    if (paddingTop < 0 && current_state != PULLDOWN_STATE) {
                        current_state = PULLDOWN_STATE;
                        LogUtil.d(TAG, "切换到下拉刷新状态");
                        switchState(current_state);
                    }else if (paddingTop > 0 && current_state != PULLDOWN_STATE) {
                        current_state = RELEASE_STATE;
                        LogUtil.d(TAG, "切换到松开刷新状态");
                        switchState(current_state);
                    }

                    header.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                downY = -1;
                //according to the current status of the judge
                //is to determine whether to switch to the refresh
                if (current_state == PULLDOWN_STATE) {
                    header.setPadding(0, -headerHeight,0,0);
                }else if (current_state == REFRESHING_STATE) {
                    current_state = REFRESHING_STATE;
                    LogUtil.d(TAG, "切换到正在刷新状态");
                    header.setPadding(0, 0, 0, 0);
                    switchState(current_state);
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onRefreshing();
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


    private interface OnRefreshListener {
        void onRefreshing();

        void onLoadingMore();
    }

    //let the outside to pass the ListView in
    public void setOnRefreshListener(OnRefreshListener listener){
        this.mOnRefreshListener = listener;
    }

    //method for providing recovery status
    public void refreshFinished(boolean success){
        tv_refresh_state.setText("下拉刷新");
        iv_refresh_image.setVisibility(View.VISIBLE);
        pb_refresh_progress.setVisibility(View.INVISIBLE);

        current_state = PULLDOWN_STATE;
        header.setPadding(0, -headerHeight, 0, 0);
        if (success) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(new Date());
            tv_refresh_time.setText("最后刷新时间："+date);
        }else{
            Toast.makeText(getContext(), "亲，网络出问题了", 0).show();
        }
    }

    private void switchState(int currentState) {
        switch (currentState) {
            case PULLDOWN_STATE:
                tv_refresh_state.setText("下拉刷新");
                iv_refresh_image.setVisibility(View.VISIBLE);
                pb_refresh_progress.setVisibility(View.INVISIBLE);
                iv_refresh_image.startAnimation(down);
                break;
            case RELEASE_STATE:
                tv_refresh_state.setText("松开刷新");
                iv_refresh_image.startAnimation(up);
                break;
            case REFRESHING_STATE:
                iv_refresh_image.clearAnimation();
                tv_refresh_state.setText("正在刷新");
                iv_refresh_image.setVisibility(View.INVISIBLE);
                pb_refresh_progress.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }

    private boolean isLoadingMore = false;//whether to load more

    private class MyOnScrollListener implements OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //Callback when status changes
            //when stop or inertia stop, to determine whether the last ListView Adapter is the last data
            //if it is, show load more
            if (OnScrollListener.SCROLL_STATE_IDLE == scrollState || OnScrollListener.SCROLL_STATE_FLING == scrollState) {
                //to determine whether the last listView item is the last on the Adapter
                if (getLastVisiblePosition() == getAdapter().getCount() - 1 && !isLoadingMore) {
                    isLoadingMore = true;
                    LogUtil.d(TAG, "加载更多中...");
                    footer.setPadding(0, 0, 0, 0);

                    setSelection(getAdapter().getCount());

                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onLoadingMore();
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }


}
