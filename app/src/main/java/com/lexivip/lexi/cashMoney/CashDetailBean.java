package com.lexivip.lexi.cashMoney;

public class CashDetailBean {
    /**
     * data : {"actual_amount":4,"arrival_time":1544780607,"created_at":1544701407,"receive_target":1,"rid":"2342345","status":1,"user_account":"omway"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * actual_amount : 4
         * arrival_time : 1544780607
         * created_at : 1544701407
         * receive_target : 1
         * rid : 2342345
         * status : 1
         * user_account : omway
         */

        public String actual_amount;
        public int arrival_time;
        public int created_at;
        public int receive_target;
        public String rid;
        public int status;
        public String user_account;
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
