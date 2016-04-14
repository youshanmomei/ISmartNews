package cn.qiuc.org.ismartnews.bean;

import java.util.List;

/**
 * Created by admin on 2016/4/14.
 */
public class NewsCenterBean {
    public List<MenuItem> data;
    public List<Integer> extend;
    public int retcode;

    public class MenuItem{
        public List<NewsTab> children;
        public int id;
        public String title;
        public String type;
        public String url;
        public String url1;
        public String dayurl;
        public String excurl;
        public String weekurl;
    }

    public class NewsTab{
        public int id;
        public String title;
        public int type;
        public String url;
    }
}
