package com.lexivip.lexi.user.register;

public class SetPasswordBean {
    /**
     * data : {"created_at":1532522531,"expiration":2592000,"token":"eyJhbGciOiJIUzI1NiIsImlhdCI6MTUzMjUyMjUzMSwiZXhwIjoxNTM1MTE0NTMxfQ.eyJpZCI6MTZ9._yfN5fiiN1hsn8kZttvHEHYwL_KbUEK1RljcLTHm6_I"}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * created_at : 1532522531
         * expiration : 2592000
         * token : eyJhbGciOiJIUzI1NiIsImlhdCI6MTUzMjUyMjUzMSwiZXhwIjoxNTM1MTE0NTMxfQ.eyJpZCI6MTZ9._yfN5fiiN1hsn8kZttvHEHYwL_KbUEK1RljcLTHm6_I
         */

        public int created_at;
        public int expiration;
        public String token;
    }

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
