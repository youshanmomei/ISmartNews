package cn.qiuc.org.ismartnews.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by admin on 2016/4/28.
 */
public class MemoryCacheUtils{
    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtils() {
        //current app maximum memory 1/8 as LruCache's memory
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);

        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}
