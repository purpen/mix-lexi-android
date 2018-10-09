package com.thn.lexi.lifeShop;

public class LifeShopCashBean {
    /**
     * data : {"cash_price":0,"total_cash_price":0}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * cash_price : 0
         * total_cash_price : 0
         */

        public String cash_price;
        public String total_cash_price;
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
