package com.lexivip.lexi.lifeShop;

import java.util.List;

public class TransactionRecordBean {
    /**
     * data : {"count":2,"not_settlement_not_read":0,"refund_not_read":0,"success_not_read":0,"transactions":[{"actual_amount":3,"order_rid":"D18081453268074","payed_at":1534232904,"status":1}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 2
         * not_settlement_not_read : 0
         * refund_not_read : 0
         * success_not_read : 0
         * transactions : [{"actual_amount":3,"order_rid":"D18081453268074","payed_at":1534232904,"status":1}]
         */

        public int count;
        public int not_settlement_not_read;
        public int refund_not_read;
        public int success_not_read;
        public List<TransactionsBean> transactions;

        public static class TransactionsBean {
            /**
             * actual_amount : 3
             * order_rid : D18081453268074
             * payed_at : 1534232904
             * status : 1
             */

            public double actual_amount;
            public String order_rid;
            public int payed_at;
            public int status;
        }
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
