package com.thn.lexi.goods.selection;

public class HeadLineBean {


    /**
     * data : {"order_count":85,"username_one":"关羽","username_two":"孙权"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * order_count : 85
         * username_one : 关羽
         * username_two : 孙权
         */

        public String order_count;
        public String username_one;
        public String username_two;
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
