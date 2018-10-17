package com.lexivip.lexi.user.password;

public class VerifyCodeBean {

    /**
     * data : {"phone_verify_code":"5702"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * phone_verify_code : 5702
         */

        public String phone_verify_code;
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
