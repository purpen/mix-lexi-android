package com.lexivip.lexi.cashMoney;

public class CashCountBean {
    /**
     * data : {"cash_count":1}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * cash_count : 1
         */

        public int cash_count;
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
