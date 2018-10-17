package com.thn.lexi.index.discover;

import java.util.List;

public class ArticleDetailBean {
    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public int audit_status;
        public int browse_count;
        public String channel_name;
        public int comment_count;
        public String content;
        public String cover;
        public int cover_id;
        public int created_at;
        public String description;
        public boolean is_follow;
        public int like_count;
        public int praise_count;
        public int published_at;
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

        public static class DealContentBean {
            /**
             * content : 在这个快节奏的社会，每个人都在不同的身份中转换，然而穿搭难免有时候会不太合时宜！一款适用于众多场合的百搭包包是每个女生所追求的！
             * rid : 6152309
             * type : text
             * big_picture : true
             */

            public String content;
            public String rid;
            public String type;
            public boolean big_picture;
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
