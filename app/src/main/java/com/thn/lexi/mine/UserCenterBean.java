package com.thn.lexi.mine;

public class UserCenterBean {

    /**
     * data : {"ID":"17048395612","about_me":"肚子好饿","avatar":"http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png","followed_stores_counts":2,"followed_users_counts":0,"fans_counts":0,"store_phases":1,"user_like_counts":0,"username":"张飞","wish_list_counts":0}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * ID : 17048395612
         * about_me : 肚子好饿
         * avatar : http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png
         * followed_stores_counts : 2
         * followed_users_counts : 0
         * fans_counts : 0
         * store_phases : 1
         * user_like_counts : 0
         * username : 张飞
         * wish_list_counts : 0
         */

        public String ID;
        public String about_me;
        public String avatar;
        public String followed_stores_counts;
        public String followed_users_counts;
        public String fans_counts;
        public String store_phases;
        public String user_like_counts;
        public String username;
        public String wish_list_counts;
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
