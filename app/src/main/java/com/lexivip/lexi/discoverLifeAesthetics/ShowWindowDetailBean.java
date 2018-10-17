package com.lexivip.lexi.discoverLifeAesthetics;

import java.util.List;

public class ShowWindowDetailBean {

    /**
     * data : {"comment_count":9,"description":"2018腾讯世界人工智能围棋大赛决赛将在中国棋院落下帷幕。","is_follow":true,"is_official":"是否官方橱窗","keywords":["棋牌","人工智能"],"like_count":2,"product_count":5,"product_covers":["http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg"],"products":[],"rid":1,"title":"橱窗标题","user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"用户名"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * comment_count : 9
         * description : 2018腾讯世界人工智能围棋大赛决赛将在中国棋院落下帷幕。
         * is_follow : true
         * is_official : 是否官方橱窗
         * keywords : ["棋牌","人工智能"]
         * like_count : 2
         * product_count : 5
         * product_covers : ["http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg"]
         * products : []
         * rid : 1
         * title : 橱窗标题
         * user_avatar : http://kg.erp.taihuoniao.com/static/img/default-logo.png
         * user_name : 用户名
         */

        public int comment_count;
        public String description;
        public boolean is_follow;
        public String is_official;
        public int like_count;
        public int product_count;
        public int rid;
        public String title;
        public String user_avatar;
        public String user_name;
        public List<String> keywords;
        public List<String> product_covers;
        public List<?> products;
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
