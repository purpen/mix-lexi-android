package com.lexivip.lexi.user.login;

public class CheckCodeLoginBean {

    /**
     * data : {"avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo-180x180.png","created_at":1532761309,"expiration":2592000,"is_distributor":true,"is_first_login":false,"is_supplier":false,"mobile":"15666666667","token":"eyJhbGciOiJIUzI1NiIsImlhdCI6MTUzMjc2MTMwOSwiZXhwIjoxNTM1MzUzMzA5fQ.eyJpZCI6MjB9.yUQsXp4j9lRgkxnsczGGPOLKRt1WpZbaN7bbBhzVj5k","uid":"18402396751","username":"15666666667"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * avatar : http://kg.erp.taihuoniao.com/static/img/default-logo-180x180.png
         * created_at : 1532761309
         * expiration : 2592000
         * is_distributor : true
         * is_first_login : false
         * is_supplier : false
         * mobile : 15666666667
         * token : eyJhbGciOiJIUzI1NiIsImlhdCI6MTUzMjc2MTMwOSwiZXhwIjoxNTM1MzUzMzA5fQ.eyJpZCI6MjB9.yUQsXp4j9lRgkxnsczGGPOLKRt1WpZbaN7bbBhzVj5k
         * uid : 18402396751
         * username : 15666666667
         */

        public String avatar;
        public int created_at;
        public int expiration;
        public boolean is_distributor;
        public boolean is_first_login;
        public boolean is_supplier;
        public String mobile;
        public String token;
        public String uid;
        public String username;
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
