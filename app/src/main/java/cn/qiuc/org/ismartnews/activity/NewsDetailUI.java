package cn.qiuc.org.ismartnews.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.qiuc.org.ismartnews.R;

public class NewsDetailUI extends Activity implements View.OnClickListener {

    @ViewInject(R.id.ib_basepager_back)
    private ImageButton ib_basepager_back;
    @ViewInject(R.id.ib_basepager_share)
    private ImageButton ib_basepager_share;
    @ViewInject(R.id.ib_basepager_textsize)
    private ImageButton ib_basepager_textsize;

    @ViewInject(R.id.webview)
    private WebView webview;

    @ViewInject(R.id.progress)
    private ProgressBar progress;

    @ViewInject(R.id.tv_basepager_title)
    private TextView tv_basepager_title;

    @ViewInject(R.id.ib_basepager_menu)
    private ImageButton ib_basepager_menu;
    private WebSettings settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail_ui);

        init();
    }

    private void init() {
        ViewUtils.inject(this);

        ib_basepager_back.setVisibility(View.VISIBLE);
        ib_basepager_share.setVisibility(View.VISIBLE);
        ib_basepager_textsize.setVisibility(View.VISIBLE);


        ib_basepager_back.setOnClickListener(this);
        ib_basepager_share.setOnClickListener(this);
        ib_basepager_textsize.setOnClickListener(this);

        tv_basepager_title.setVisibility(View.INVISIBLE);
        ib_basepager_menu.setVisibility(View.INVISIBLE);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //when page load finished, callback it
                progress.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });

        settings = webview.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");
        webview.loadUrl(url);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_basepager_back:
                finish();
                break;
            case R.id.ib_basepager_textsize:
                showTextsize();
                break;
            case R.id.ib_basepager_share:
                showShare();
                break;
            default:
                break;
        }

    }

    private void showShare() {
        /*
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();

        oks.disablesSSOWhenAuthorize();

        oks.setText("发个微博");
        oks.setImagePath("/sdcard/test.jpg");
        oks.show();
        */
        Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
    }

    private int currentIndex = 2;
    private int tempIndex = 0;
    private void showTextsize() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
        builder.setSingleChoiceItems(items, currentIndex, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempIndex = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentIndex = tempIndex;
                changeTextSie(currentIndex);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

    private void changeTextSie(int index) {
        switch (index) {
            case 0:
                settings.setTextSize(WebSettings.TextSize.LARGEST);
                break;
            case 1:
                settings.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 2:
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                settings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 4:
                settings.setTextSize(WebSettings.TextSize.SMALLEST);
                break;
            default:
                break;
        }
    }


}
