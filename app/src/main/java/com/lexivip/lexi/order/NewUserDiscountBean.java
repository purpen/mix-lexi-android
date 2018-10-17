package com.lexivip.lexi.order;

public class NewUserDiscountBean {

    /**
     * data : {"is_new_user":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * is_new_user : false
         */
        public double discount_ratio;
        public boolean is_new_user;
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
