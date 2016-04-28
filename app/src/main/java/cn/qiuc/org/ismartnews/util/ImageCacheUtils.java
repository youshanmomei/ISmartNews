package cn.qiuc.org.ismartnews.util;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by admin on 2016/4/27.
 */
public class ImageCacheUtils {
    private final String TAG = "ImageCacheUtils";

    private final MemoryCacheUtils memoryCacheUtils;
    private final LocalCacheUtils localCacheUtils;
    private final NetCacheUtils netCacheUtils;

    public ImageCacheUtils() {
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils(memoryCacheUtils);
        netCacheUtils = new NetCacheUtils(memoryCacheUtils, localCacheUtils);

    }

    public void display(ImageView imageView, String url, ListView list){
        Bitmap bitmap = null;

        bitmap = memoryCacheUtils.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            LogUtil.d(TAG, "从内存缓存中获取图片");
            return;
        }

        bitmap = localCacheUtils.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            LogUtil.d(TAG, "从本地缓存获取图片");
            return;
        }

        LogUtil.d(TAG,"访问网络获取图片");
        netCacheUtils.display(url, imageView, list);
    }
}
