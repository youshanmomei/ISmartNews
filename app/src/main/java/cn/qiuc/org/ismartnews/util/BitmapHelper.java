package cn.qiuc.org.ismartnews.util;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by admin on 2016/4/23.
 */
public class BitmapHelper {

    public static BitmapUtils bitmapUtils;
    private BitmapHelper(){}

    public static BitmapUtils getBitmapUtils(Context context) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(context);
        }
        return bitmapUtils;
    }
}
