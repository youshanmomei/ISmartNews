package cn.qiuc.org.ismartnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.util.LogUtil;
import cn.qiuc.org.ismartnews.util.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String IS_APP_FIRST_OPEN = "is_app_first_open";
    private RelativeLayout rl_welcome_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        rl_welcome_bg = (RelativeLayout) findViewById(R.id.rl_welcome_bg);

        //rotate
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        //keep it at the end of the animation
        rotateAnimation.setFillAfter(true);

        //zoom
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        //gradient
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        //weather share the same accelerator
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);

        rl_welcome_bg.startAnimation(animationSet);
        //monitor animation
        animationSet.setAnimationListener(new MyAnimationListener());

    }

    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //jump view and get value after the animation
            //determine whether it is the first time to open
            boolean isFirstOpen = SharedPreferencesUtil.getBoolean(getApplicationContext(), IS_APP_FIRST_OPEN, true);
            if (isFirstOpen) {
                LogUtil.d(TAG, "第一次打开，跳转到引导界面");
                startActivity(new Intent(MainActivity.this, GuideUI.class));
            } else {
                LogUtil.d(TAG, "不是第一次打开，跳转到主界面");
                startActivity(new Intent(MainActivity.this, MainUI.class));
            }

            finish();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
