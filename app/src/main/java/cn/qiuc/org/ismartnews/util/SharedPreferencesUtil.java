package cn.qiuc.org.ismartnews.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2016/4/11.
 */
public class SharedPreferencesUtil {

    private static final String SP_NAME = "config";
    private static SharedPreferences sp;

    public static void remove(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, context.MODE_MULTI_PROCESS);
        }
        sp.edit().remove(key);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, context.MODE_MULTI_PROCESS);
        }

        return sp.getBoolean(key, defValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, context.MODE_MULTI_PROCESS);
        }

        sp.edit().putBoolean(key,value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, context.MODE_MULTI_PROCESS);
        }
        return sp.getString(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, context.MODE_MULTI_PROCESS);
        }

        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        }

        return sp.getInt(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, context.MODE_MULTI_PROCESS);
        }

        sp.edit().putInt(key, value).commit();
    }
}
