package cn.qiuc.org.ismartnews.bean;

import java.util.List;

import cn.qiuc.org.ismartnews.domain.TabDetailBean;

/**
 * Created by admin on 2016/4/29.
 */
public class PhotoBean {
    public int retcode;
    public Data data;

    public class Data{
        public String more;
        public String countcommenturl;
        public String title;
        public List<TabDetailBean.Data.News> news;

        public class News{
            public String commentlist;
            public String largeimage;
            public String commenturl;
            public Boolean comment;
            public int id;
            public String title;
            public String type;
            public String listimage;
            public String smallimage;
            public String url;
            public String pubdata;
        }

        public List<Topic> topic;

        public class Topic{}
    }

}
