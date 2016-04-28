package cn.qiuc.org.ismartnews.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2016/4/28.
 */
public class NetCacheUtils {

    private LocalCacheUtils loaclCacheUtils;
    private ExecutorService pool;
    private MemoryCacheUtils memoryCacheUtils;
    private ListView listview;
    private Handler handler;
    private int SUCCESS=1;

    public NetCacheUtils(MemoryCacheUtils memoryCacheUtils, LocalCacheUtils localCacheUtils) {
        pool = Executors.newFixedThreadPool(5);
        this.memoryCacheUtils = memoryCacheUtils;
        this.loaclCacheUtils = localCacheUtils;
    }

    public void display(String url, ImageView imageView, ListView list) {
        pool.execute(new DownLoadRunnable(url, imageView));
        handler = new MyHandler();
        this.listview = list;
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Result res = (Result) msg.obj;
            ImageView imageView = (ImageView) listview.findViewWithTag(res.position);
            if (imageView != null) {
                imageView.setImageBitmap(res.bitmap);
            }

            super.handleMessage(msg);
        }
    }


    private class DownLoadRunnable implements Runnable {

        private String mUrl;
        private ImageView mImageView;
        private int position;

        public DownLoadRunnable(String url, ImageView imageView) {
            mUrl = url;
            mImageView = imageView;
            position = (Integer)imageView.getTag();
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                HttpURLConnection con = (HttpURLConnection) new URL(mUrl).openConnection();
                con.connect();

                int resCode = con.getResponseCode();
                if (resCode == 200) {
                    InputStream is = con.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    Message.obtain(handler, SUCCESS, new Result(position, bitmap)).sendToTarget();

                    memoryCacheUtils.putBitmap(mUrl, bitmap);
                    loaclCacheUtils.saveBitmap(mUrl, bitmap);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Result{
        public ImageView imageView;
        public Bitmap bitmap;
        public int position;


        public Result(ImageView imageView, Bitmap bitmap) {
            this.imageView = imageView;
            this.bitmap = bitmap;
        }

        public Result(int position, Bitmap bitmap) {
            this.position = position;
            this.bitmap = bitmap;
        }
    }
}
