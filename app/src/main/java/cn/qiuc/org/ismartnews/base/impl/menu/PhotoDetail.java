package cn.qiuc.org.ismartnews.base.impl.menu;

import android.content.Context;
import android.view.View;

import com.lidroid.xutils.BitmapUtils;

import cn.qiuc.org.ismartnews.base.MenuDetailBasePager;
import cn.qiuc.org.ismartnews.util.BitmapHelper;
import cn.qiuc.org.ismartnews.util.ImageCacheUtils;

/**
 * Created by admin on 2016/4/27.
 */
public class PhotoDetail extends MenuDetailBasePager {

    private final BitmapUtils bitmapUtils;
    private final ImageCacheUtils imageCacheUtils;

    public PhotoDetail(Context context) {
        super(context);
        bitmapUtils = BitmapHelper.getBitmapUtils(mContext);
        imageCacheUtils = new ImageCacheUtils();//TODO...

    }

    @Override
    protected View initView() {
        return null;
    }
}
