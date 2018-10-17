package com.lexivip.lexi.index.detail;

public class ShopCartProductNumBean {

    /**
     * data : {"item_count":2}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * item_count : 2
         */

        public int item_count;
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
