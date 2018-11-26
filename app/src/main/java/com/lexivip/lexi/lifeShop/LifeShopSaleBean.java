package com.lexivip.lexi.lifeShop;

public class LifeShopSaleBean {
    /**
     * data : {"pending_commission_price":7,"today_commission_price":0,"total_commission_price":"10 .00"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * pending_commission_price : 7.0
         * today_commission_price : 0.0
         * total_commission_price : 10.00
         */

        public String pending_commission_price;
        public String today_commission_price;
        public String total_commission_price;
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
