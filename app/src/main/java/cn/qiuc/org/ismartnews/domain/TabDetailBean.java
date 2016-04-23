package cn.qiuc.org.ismartnews.domain;

import java.util.List;

/**
 * Created by admin on 2016/4/23.
 */
public class TabDetailBean {
    public int retcode;
    public Data data;

    public class Data {
        public String more;
        public String countcommenturl;
        public String title;
        public List<News> news;
        public List<TopNews> topNewses;
        public List<Topic> topic;

        public class News {
            public String commentlist;
            public String commenturl;
            public Boolean comment;
            public int id;
            public String title;
            public String type;
            public String listimage;
            public String url;
            public String pubdate;
        }

        public class TopNews {
            public String commentlist;
            public String topimage;
            public String commenturl;
            public Boolean comment;
            public int id;
            public String title;
            public String type;
            public String url;
            public String pubdate;
        }

        public class Topic {
            public String description;
            public int id;
            public int sort;
            public String title;
            public String listimage;
            public String url;
        }
    }
}
