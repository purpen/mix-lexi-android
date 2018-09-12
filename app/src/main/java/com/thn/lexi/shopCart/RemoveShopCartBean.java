package com.thn.lexi.shopCart;

public class RemoveShopCartBean {

    /**
     * data : {"item_count":2}
     * status : {"code":204,"message":"All deleted."}
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
         * code : 204
         * message : All deleted.
         */

        public int code;
        public String message;
    }
}
