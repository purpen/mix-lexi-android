package com.lexivip.lexi.user.login;


public class ApplyForLifeHouseBean {
    /**
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */
    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {

        public String store_rid;
        public Boolean is_small_b;
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
