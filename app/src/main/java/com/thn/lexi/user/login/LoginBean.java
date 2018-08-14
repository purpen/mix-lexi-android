package com.thn.lexi.user.login;

/**
 * Created by lilin on 2018/3/12.
 */

public class LoginBean {

    /**
     * data : {"created_at":1533723438,"expiration":2592000,"is_first_login":false,"is_small_b":null,"is_supplier":true,"store_rid":"99130748","token":"eyJhbGciOiJIUzI1NiIsImlhdCI6MTUzMzcyMzQzOCwiZXhwIjoxNTM2MzE1NDM4fQ.eyJpZCI6MjgzfQ.3I5ItefibD8vSMHHBnWsrASTu1ybNW_yQB1QRPpJJAg"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * created_at : 1533723438
         * expiration : 2592000
         * is_first_login : false
         * token : eyJhbGciOiJIUzI1NiIsImlhdCI6MTUzMzcyMzQzOCwiZXhwIjoxNTM2MzE1NDM4fQ.eyJpZCI6MjgzfQ.3I5ItefibD8vSMHHBnWsrASTu1ybNW_yQB1QRPpJJAg
         */

        public int created_at;
        public int expiration;
        public boolean is_first_login;
        public String token;
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
