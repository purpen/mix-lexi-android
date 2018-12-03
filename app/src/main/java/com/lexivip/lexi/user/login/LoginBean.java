package com.lexivip.lexi.user.login;

/**
 * Created by lilin on 2018/3/12.
 */

public class LoginBean {
    /**
     * data : {"avatar":"https://static.moebeast.com/image/static/default_user.png","created_at":1543828545,"expiration":2592000,"is_first_login":false,"token":"eyJhbGciOiJIUzI1NiIsImlhdCI6MTU0MzgyODU0NSwiZXhwIjoxNTQ2NDIwNTQ1fQ.eyJpZCI6MTYzNjB9.hu56GQQ2RWaTcqOu-gKqNuD3thDPaHiM1VKIClT7r5A","uid":"19875603241","username":"13611086684"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * avatar : https://static.moebeast.com/image/static/default_user.png
         * created_at : 1543828545
         * expiration : 2592000
         * is_first_login : false
         * token : eyJhbGciOiJIUzI1NiIsImlhdCI6MTU0MzgyODU0NSwiZXhwIjoxNTQ2NDIwNTQ1fQ.eyJpZCI6MTYzNjB9.hu56GQQ2RWaTcqOu-gKqNuD3thDPaHiM1VKIClT7r5A
         * uid : 19875603241
         * username : 13611086684
         */

        public String avatar;
        public int created_at;
        public int expiration;
        public boolean is_first_login;
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
