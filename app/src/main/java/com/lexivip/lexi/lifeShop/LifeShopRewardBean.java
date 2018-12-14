package com.lexivip.lexi.lifeShop;

public class LifeShopRewardBean {
    /**
     * data : {"cash_amount":0,"cumulative_cash_amount":0,"pending_price":1,"reward_price":0}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * cash_amount : 0
         * cumulative_cash_amount : 0
         * pending_price : 1
         * reward_price : 0
         */

        public String cash_amount;
        public String cumulative_cash_amount;
        public String pending_price;
        public String reward_price;
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
