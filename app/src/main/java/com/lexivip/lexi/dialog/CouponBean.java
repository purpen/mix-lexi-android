package com.lexivip.lexi.dialog;

public class CouponBean {
    /**
     * data : {"is_grant":0}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * is_grant : 0
         */

        public int is_grant;
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
