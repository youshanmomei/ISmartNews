package cn.qiuc.org.ismartnews.base.impl.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.base.MenuDetailBasePager;
import cn.qiuc.org.ismartnews.bean.PhotoBean;
import cn.qiuc.org.ismartnews.domain.TabDetailBean;
import cn.qiuc.org.ismartnews.util.BitmapHelper;
import cn.qiuc.org.ismartnews.util.ConstantUtils;
import cn.qiuc.org.ismartnews.util.ImageCacheUtils;

/**
 * Created by admin on 2016/4/27.
 */
public class PhotoDetailPager extends MenuDetailBasePager {

    private final BitmapUtils bitmapUtils;
    private final ImageCacheUtils imageCacheUtils;
    private ListView list;
    private GridView grid;
    private List<TabDetailBean.Data.News> newsData;

    public PhotoDetailPager(Context context) {
        super(context);
        bitmapUtils = BitmapHelper.getBitmapUtils(mContext);
        imageCacheUtils = new ImageCacheUtils();

    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.photo, null);
        list = (ListView) view.findViewById(R.id.lv_photo_list);
        grid = (GridView) view.findViewById(R.id.gv_photo_grid);
        return view;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }


    public void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, ConstantUtils.PHOTO_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                processData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        PhotoBean photoBean = gson.fromJson(result, PhotoBean.class);

        newsData = photoBean.data.news;
        list.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {
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
            PhotoHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.photo_item, null);
                holder = new PhotoHolder();
                holder.photo = (ImageView) convertView.findViewById(R.id.iv_photoitem_photo);
                holder.title = (TextView) convertView.findViewById(R.id.tv_newsitem_title);
                convertView.setTag(holder);
            } else {
                holder = (PhotoHolder) convertView.getTag();
            }
            TabDetailBean.Data.News news = newsData.get(position);
            holder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);

            holder.photo.setTag(position);

            holder.photo.setImageResource(R.mipmap.photo_item_d);
            imageCacheUtils.display(holder.photo, news.listimage, list);
            holder.title.setText(news.title);

            return convertView;
        }

        class PhotoHolder {
            public ImageView photo;
            public TextView title;
        }

        private boolean isListType = true;
        private ImageCacheUtils imageCacheUtils;

        public void switchPhoto(ImageButton ib_basepager_phototype) {
            if (isListType) {
                ib_basepager_phototype.setBackgroundResource(R.mipmap.icon_pic_list_type);
                list.setVisibility(View.GONE);
                grid.setVisibility(View.VISIBLE);
                grid.setAdapter(new MyAdapter());
                isListType = false;
            }else{
                ib_basepager_phototype.setBackgroundResource(R.mipmap.icon_pic_grid_type);
                list.setVisibility(View.VISIBLE);
                grid.setVisibility(View.GONE);
                list.setAdapter(new MyAdapter());
                isListType = true;
            }

        }
    }
}
