package cn.qiuc.org.ismartnews.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.fragment.ContentFragment;
import cn.qiuc.org.ismartnews.fragment.LeftFragment;

public class MainUI extends SlidingFragmentActivity {

    private static final String LEFT_TAG = "left_tag";
    private static final String MAIN_TAG = "main_tag";
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set left menu
        setBehindContentView(R.layout.left);
        //set content
        setContentView(R.layout.activity_main_ui);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //set the width of the text
        slidingMenu.setBehindOffset(200);

        initFragment();

    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rl_left, new Fragment(), LEFT_TAG);
        transaction.replace(R.id.rl_main, new Fragment(), MAIN_TAG);
        transaction.commit();
    }

    public LeftFragment getLeftFragment() {
        return (LeftFragment) fragmentManager.findFragmentByTag(LEFT_TAG);
    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) fragmentManager.findFragmentByTag(MAIN_TAG);
    }
}
