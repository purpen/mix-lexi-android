package com.lexivip.lexi.brandHouse;

public class BrandHouseFollowBean {
    /**
     * data : {"fans_count":1,"status":true}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * fans_count : 1
         * status : true
         */

        public int fans_count;
        public boolean status;
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
