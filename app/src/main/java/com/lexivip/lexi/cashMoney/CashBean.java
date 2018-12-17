package com.lexivip.lexi.cashMoney;

public class CashBean {
    /**
     * data : {"amount":0,"friends_count":0,"can_carry_amount":0}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * amount : 0
         * friends_count : 0
         * can_carry_amount : 0
         */

        public String amount;
        public int friends_count;
        public String can_carry_amount;
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
