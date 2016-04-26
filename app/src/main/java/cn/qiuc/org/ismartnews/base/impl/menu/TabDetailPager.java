package cn.qiuc.org.ismartnews.base.impl.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.activity.NewsDetailUI;
import cn.qiuc.org.ismartnews.base.MenuDetailBasePager;
import cn.qiuc.org.ismartnews.bean.NewsCenterBean;
import cn.qiuc.org.ismartnews.domain.TabDetailBean;
import cn.qiuc.org.ismartnews.util.BitmapHelper;
import cn.qiuc.org.ismartnews.util.ConstantUtils;
import cn.qiuc.org.ismartnews.util.LogUtil;
import cn.qiuc.org.ismartnews.util.SharedPreferencesUtil;
import cn.qiuc.org.ismartnews.view.RefreshListView;

/**
 * Created by admin on 2016/4/23.
 */
public class TabDetailPager extends MenuDetailBasePager {
    private static final String TAG = "TabDetailPager";
    private final NewsCenterBean.NewsTab tabData;
    private List<TabDetailBean.Data.TopNews> topnewsData;

    @ViewInject(R.id.vp_tabdetail_topimage)
    private ViewPager vp_tabdetail_topimage;
    @ViewInject(R.id.tv_tabdetail_imginfo)
    private TextView tv_tabdetail_imginfo;
    @ViewInject(R.id.ll_tabdetail_points)
    private LinearLayout ll_tabdetail_points;
    @ViewInject(R.id.lv_tabdetail_list)
    private RefreshListView lv_tabdetail_list;
    private BitmapUtils bitmapUtils;
    private List<TabDetailBean.Data.TopNews> topnewsData1;
    private int prePointIndex;
    private List<TabDetailBean.Data.News> newsData;
    private NewsAdapter adapter;
    private MyHandler handler;
    private final String NEWS_ID_READED = "news_id_readed";

    public TabDetailPager(Context context, NewsCenterBean.NewsTab newsTab) {
        super(context);
        this.tabData = newsTab;
        BitmapHelper.getBitmapUtils(mContext);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.tabdetail, null);
        ViewUtils.inject(this, view);

        //load carousel figure layout in the top
        View topnews = View.inflate(mContext, R.layout.topnews, null);
        ViewUtils.inject(this, topnews);

        lv_tabdetail_list.addHeaderView(topnews);
        lv_tabdetail_list.setOnRefreshListener(new MyOnRefreshListener());
        lv_tabdetail_list.setOnItemClickListener(new MyOnItemClickListener());

        return view;
    }

    private boolean isRefreshing;
    private String moreUrl;

    private class MyOnRefreshListener implements RefreshListView.OnRefreshListener {
        @Override
        public void onRefreshing() {
            isRefreshing = true;
            //handle drop down refresh
            getDataFromServer();
        }

        @Override
        public void onLoadingMore() {
            if (!TextUtils.isEmpty(moreUrl)) {
                isLoadMore = true;
                getMoreDataFromServer();
            } else {
                lv_tabdetail_list.loadMoreFinished();
                Toast.makeText(mContext, "亲，没有更多数据了", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void getMoreDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.configDefaultHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantUtils.BASE_URL + moreUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtil.d(TAG, "加载更多数据成功："+ responseInfo.result);
                processData(responseInfo.result);

                isLoadMore = false;
                lv_tabdetail_list.loadMoreFinished();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                LogUtil.e(TAG, "加载更多数据失败");
                isLoadMore = false;
                lv_tabdetail_list.loadMoreFinished();
            }
        });
    }

    @Override
    public void initData() {
//        super.initData();
        String cacheJson = SharedPreferencesUtil.getString(mContext, tabData.url, "");
        if (!TextUtils.isEmpty(cacheJson)) {
            processData(cacheJson);
        }

        getDataFromServer();

    }

    private boolean isLoadMore = false;

    private void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.configDefaultHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantUtils.BASE_URL + tabData.url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtil.d(TAG, tabData.title + " 访问网络成功: " + responseInfo.result);
                SharedPreferencesUtil.putString(mContext, tabData.url, responseInfo.result);
                processData(responseInfo.result);

                if (isRefreshing) {
                    lv_tabdetail_list.refreshFinished(true);
                    isRefreshing = false;
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                LogUtil.d(TAG, "访问网络失败");
                if (isRefreshing) {
                    lv_tabdetail_list.refreshFinished(false);
                    isRefreshing = false;
                }
            }
        });

    }

    private void processData(String result) {
        Gson gson = new Gson();
        TabDetailBean tabDetailBean = gson.fromJson(result, TabDetailBean.class);
        String moreUrl = tabDetailBean.data.more;
        if (!isLoadMore) {
            topnewsData1 = tabDetailBean.data.topNewses;
            vp_tabdetail_topimage.setAdapter(new TopAdapter());
            vp_tabdetail_topimage.setOnPageChangeListener(new MyOnPageChangeListener());
            tv_tabdetail_imginfo.setText(topnewsData.get(0).title);

            prePointIndex = 0;
            ll_tabdetail_points.removeAllViews();

            for (int i = 0; i < topnewsData.size(); i++) {
                ImageView imageView = new ImageView(mContext);
                imageView.setBackgroundResource(R.drawable.topnews_dot_selector);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
                imageView.setLayoutParams(params);
                params.leftMargin = 10;
                imageView.setEnabled(false);
                ll_tabdetail_points.addView(imageView);
            }

            ll_tabdetail_points.getChildAt(0).setEnabled(true);

            newsData = tabDetailBean.data.news;

            adapter = new NewsAdapter();
            lv_tabdetail_list.setAdapter(adapter);

        } else {
            newsData.addAll(tabDetailBean.data.news);
            adapter.notifyDataSetChanged();
        }

        if (handler == null) {
            handler = new MyHandler();
        }

        handler.removeCallbacksAndMessages(null);
        handler.sendMessageDelayed(Message.obtain(handler), 3000);
    }

    class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.news_list_item, null);
                holder = new NewsHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.iv_newsitem_image);
                holder.title = (TextView) convertView.findViewById(R.id.tv_newsitem_title);
                holder.time = (TextView) convertView.findViewById(R.id.tv_newsitem_time);
                convertView.setTag(holder);
            } else {
                holder = (NewsHolder) convertView.getTag();
            }

            TabDetailBean.Data.News news = newsData.get(position);
            String cacheId = SharedPreferencesUtil.getString(mContext, NEWS_ID_READED, "");
            if (cacheId.contains(String.valueOf(news.id))) {
                holder.title.setTextColor(Color.RED);
            } else {
                holder.title.setTextColor(Color.BLACK);
            }

            holder.title.setText(news.title);
            holder.time.setText(news.pubdate);
            bitmapUtils.display(holder.imageView, news.listimage);

            return convertView;
        }
    }

    class NewsHolder {
        public ImageView imageView;
        public TextView title;
        public TextView time;
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (vp_tabdetail_topimage.getWindowVisibility() == View.GONE) {
                handler.removeCallbacksAndMessages(null);
                return;
            }

            int nextIndex = (vp_tabdetail_topimage.getCurrentItem() + 1) % topnewsData.size();
            vp_tabdetail_topimage.setCurrentItem(nextIndex);
            handler.sendMessageDelayed(Message.obtain(handler), 3000);

            super.handleMessage(msg);
        }
    }

    private class TopAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return topnewsData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            container.addView(imageView);

            imageView.setScaleType(ImageView.ScaleType.CENTER.CENTER_CROP);
            bitmapUtils.display(imageView, topnewsData.get(position).topimage);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            LogUtil.d(TAG, "手指按下时，删除所有消息，停止轮播");
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            LogUtil.d(TAG, "手指抬起，再发消息，继续轮播");
                            handler.sendMessageDelayed(Message.obtain(handler), 3000);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            LogUtil.d(TAG, "事件取消，继续轮播");
                            handler.sendMessageDelayed(Message.obtain(handler), 3000);
                        default:
                            break;
                    }
                    return true;
                }
            });

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ll_tabdetail_points.getChildAt(prePointIndex).setEnabled(false);
            tv_tabdetail_imginfo.setText(topnewsData.get(position).title);
            ll_tabdetail_points.getChildAt(position).setEnabled(true);
            prePointIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPosition = position -2;
            TabDetailBean.Data.News news = newsData.get(realPosition);

            String cacheId = SharedPreferencesUtil.getString(mContext, NEWS_ID_READED, "");
            String newsid = String.valueOf(news.id);
            String tempId = "";

            if (!cacheId.contains(newsid)) {
                tempId = cacheId + "," + newsid;
                SharedPreferencesUtil.putString(mContext, NEWS_ID_READED, tempId);
                adapter.notifyDataSetChanged();
            }

            Intent intent = new Intent(mContext, NewsDetailUI.class);
            intent.putExtra("url", news.url);
            mContext.startActivity(intent);
        }
    }
}
