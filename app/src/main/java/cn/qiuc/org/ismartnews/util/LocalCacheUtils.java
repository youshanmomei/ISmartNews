package cn.qiuc.org.ismartnews.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by admin on 2016/4/28.
 */
public class LocalCacheUtils {
    private String CACHE_DIR;
    private MemoryCacheUtils memoryCacheUtils;

    public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils) {
        CACHE_DIR = "/sdcard/zhbj73";
        this.memoryCacheUtils = memoryCacheUtils;
    }

    /**
     * get picture from local
     * @param url
     * @return
     */
    public Bitmap getBitmap(String url){

        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_DIR, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                memoryCacheUtils.putBitmap(url, bitmap);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * save bitmap to local
     * @param url
     * @param bitmap
     */
    public void saveBitmap(String url, Bitmap bitmap) {
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_DIR, fileName);

            //to determine whether there is a parent directory
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            //persistent Bitmap objects to the file
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
