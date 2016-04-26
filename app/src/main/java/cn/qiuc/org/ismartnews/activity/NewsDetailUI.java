package cn.qiuc.org.ismartnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import cn.qiuc.org.ismartnews.R;

public class NewsDetailUI extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail_ui);

        //init();TODO...
    }
}
