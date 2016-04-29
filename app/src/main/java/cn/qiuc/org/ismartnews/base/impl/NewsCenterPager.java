package cn.qiuc.org.ismartnews.base.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.qiuc.org.ismartnews.activity.MainUI;
import cn.qiuc.org.ismartnews.base.BasePager;
import cn.qiuc.org.ismartnews.base.MenuDetailBasePager;
import cn.qiuc.org.ismartnews.base.impl.menu.InterractDetailPager;
import cn.qiuc.org.ismartnews.base.impl.menu.NewsDetailPager;
import cn.qiuc.org.ismartnews.base.impl.menu.PhotoDetailPager;
import cn.qiuc.org.ismartnews.base.impl.menu.TopicDeatilPager;
import cn.qiuc.org.ismartnews.bean.NewsCenterBean;
import cn.qiuc.org.ismartnews.fragment.LeftFragment;
import cn.qiuc.org.ismartnews.util.ConstantUtils;
import cn.qiuc.org.ismartnews.util.LogUtil;
import cn.qiuc.org.ismartnews.util.SharedPreferencesUtil;

/**
 * Created by admin on 2016/4/20.
 */
public class NewsCenterPager extends BasePager {
    private final String TAG = "NewsCenterPager";
    private List<MenuDetailBasePager> menuPagers;
    private final String NEWSCENTER_CACHE_JSON = "newscenter_cache_json";
    private NewsCenterBean newsCenterBean;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        LogUtil.d(TAG, "新闻中心加载了");
        tv_basepager_title.setText("新闻中心");

        //get cache data
        //if there is a cache of data
        //the cache date is display
        String cacheJson = SharedPreferencesUtil.getString(mContext, NEWSCENTER_CACHE_JSON, "");
        if (TextUtils.isEmpty(cacheJson)) {
            processData(cacheJson);
        }

        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        //httpUtils default cache data, default time is 60s
        httpUtils.configDefaultHttpCacheExpiry(0);

        //generic T, on behalf of the server to return the type of json data is String
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantUtils.NEWSCENTER_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtil.d(TAG, "新闻中心访问网络成功：" + responseInfo.result);

                //cache data
                SharedPreferencesUtil.putString(mContext, NEWSCENTER_CACHE_JSON, responseInfo.result);
                processData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                LogUtil.d(TAG, "新闻中心访问网络数据失败");
            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        newsCenterBean = gson.fromJson(result, NewsCenterBean.class);

        //get the left menu object
        MainUI mainUI = (MainUI) mContext;
        LeftFragment leftFragment = mainUI.getLeftFragment();

        //pass data to the left menu
        leftFragment.setMenuData(newsCenterBean.data);

        menuPagers = new ArrayList<MenuDetailBasePager>();
        menuPagers.add(new NewsDetailPager(mContext, newsCenterBean.data.get(0).children));
        menuPagers.add(new TopicDeatilPager(mContext));
        menuPagers.add(new PhotoDetailPager(mContext));
        menuPagers.add(new InterractDetailPager(mContext));
        //...

        //the default display details of the default view
        switchPager(0);
    }

    //base the click position of the left menu to modify the Fragment
    private void switchPager(int position) {
        tv_basepager_title.setText(newsCenterBean.data.get(position).title);
        MenuDetailBasePager menuDetailBasePager = menuPagers.get(position);

        fl_basepager_content.removeAllViews();
        fl_basepager_content.addView(menuDetailBasePager.rootView);

        menuDetailBasePager.initData();

        //if click on the photos
        //display the photos button
        if (position == 2) {
            ib_basepager_phototype.setVisibility(View.VISIBLE);
//            final PhotoDetailBasePager pager = menuPagers.get(2);TODO
            ib_basepager_phototype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    pager.swithcPhotoType(ib_basepager_phototype);TODO
                }
            });
        } else {
            ib_basepager_phototype.setVisibility(View.GONE);
        }
    }
}
