package com.lexivip.lexi.lifeShop;

public class LifeShopFriendBean {
    /**
     * data : {"invite_count":1,"reward_price":0,"today_count":0}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * invite_count : 1
         * reward_price : 0
         * today_count : 0
         */

        public String invite_count;
        public String reward_price;
        public String today_count;
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
