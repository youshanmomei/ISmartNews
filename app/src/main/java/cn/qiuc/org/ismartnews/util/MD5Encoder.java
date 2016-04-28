package cn.qiuc.org.ismartnews.util;

import java.security.MessageDigest;

/**
 * Created by admin on 2016/4/28.
 */
public class MD5Encoder {

    public static String encode(String string) throws Exception {
        byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xff) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xff));
        }
        return hex.toString();
    }
}
