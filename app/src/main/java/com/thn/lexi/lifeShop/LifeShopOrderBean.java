package com.thn.lexi.lifeShop;

public class LifeShopOrderBean {
    /**
     * data : {"all_count":11,"today_count":0}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * all_count : 11
         * today_count : 0
         */

        public int all_count;
        public int today_count;
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
