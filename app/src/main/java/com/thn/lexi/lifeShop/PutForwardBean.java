package com.thn.lexi.lifeShop;

public class PutForwardBean {
    /**
     * data : {"actual_account_amount":11,"actual_amount":11,"created_at":1534581237,"receive_target":1,"record_id":3,"service_fee":0,"status":2,"store_rid":"2"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * actual_account_amount : 11.0
         * actual_amount : 11.0
         * created_at : 1534581237
         * receive_target : 1
         * record_id : 3
         * service_fee : 0.0
         * status : 2
         * store_rid : 2
         */

        public double actual_account_amount;
        public double actual_amount;
        public int created_at;
        public int receive_target;
        public int record_id;
        public double service_fee;
        public int status;
        public String store_rid;
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
