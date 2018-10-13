package com.thn.lexi.selectionGoodsCenter;

public class PutAwayBean {

    /**
     * data : {"is_distributed":true,"product_packet_id":0,"status":false,"stick_text":"分销推荐语......"}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * is_distributed : true
         * product_packet_id : 0
         * status : false
         * stick_text : 分销推荐语......
         */

        public boolean is_distributed;
        public int product_packet_id;
        public boolean status;
        public String stick_text;
    }

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
