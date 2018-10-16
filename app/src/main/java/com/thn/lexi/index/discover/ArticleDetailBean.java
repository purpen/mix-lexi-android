package com.thn.lexi.index.discover;

import java.util.List;

public class ArticleDetailBean {

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {

        public int audit_status;
        public int browse_count;
        public int channel_id;
        public String channel_name;
        public int comment_count;
        public String content;
        public String cover;
        public int cover_id;
        public int created_at;
        public String description;
        public boolean is_follow;
        public int label_id;
        public String label_name;
        public int like_count;
        public int praise_count;
        public long published_at;
        public RecommendStoreBean recommend_store;
        public String refuse_reason;
        public int rid;
        public int status;
        public String store_logo;
        public String store_name;
        public String title;
        public int type;
        public String uid;
        public String user_avator;
        public String user_name;
        public List<DealContentBean> deal_content;

        public static class RecommendStoreBean {
            /**
             * bgcover : https://s3.lexivip.com/lxServer/1529890796184.jpg
             * city : 宁波市
             * country : 中国
             * delivery_city : 宁波市
             * delivery_country : 中国
             * delivery_country_id : 1
             * delivery_province : 浙江
             * distribution_type : 0
             * fans_count : 0
             * is_follow_store : false
             * product_counts : 17
             * province : 浙江
             * store_logo : https://s3.lexivip.com/lxServer/1529890348135.png
             * store_name : ZHDD拙见原创趣味皮具设计
             * store_rid : 98671590
             * tag_line : 主张以孩童的眼睛，去观察这个世界，赋予一切以稚趣。以皮具
             * town :
             */

            public String bgcover;
            public String city;
            public String country;
            public String delivery_city;
            public String delivery_country;
            public int delivery_country_id;
            public String delivery_province;
            public int distribution_type;
            public int fans_count;
            public boolean is_follow_store;
            public int product_counts;
            public String province;
            public String store_logo;
            public String store_name;
            public String store_rid;
            public String tag_line;
            public String town;
        }

        public static class DealContentBean {
            /**
             * content : 【乐喜创作人档案】
             * rid : 3620985
             * type : text
             */

            public String content;
            public String rid;
            public String type;
        }
    }

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
