package com.lexivip.lexi.lifeShop;

import java.util.HashMap;

public class AccountDetailBean {
    /**
     * data : {"life_cash_record_dict":{"actual_amount":22,"created_at":1534582337,"order_info":{"8月":{"D18082013567290":{"commission_price":6,"created_at":1534763366}}},"receive_target":1,"record_id":1,"service_fee":0,"status":2,"store_rid":"2"}}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * life_cash_record_dict : {"actual_amount":22,"created_at":1534582337,"order_info":{"8月":{"D18082013567290":{"commission_price":6,"created_at":1534763366}}},"receive_target":1,"record_id":1,"service_fee":0,"status":2,"store_rid":"2"}
         */

        public LifeCashRecordDictBean life_cash_record_dict;

        public static class LifeCashRecordDictBean {
            /**
             * actual_amount : 22
             * created_at : 1534582337
             * order_info : {"8月":{"D18082013567290":{"commission_price":6,"created_at":1534763366}}}
             * receive_target : 1
             * record_id : 1
             * service_fee : 0.0
             * status : 2
             * store_rid : 2
             */

            public String actual_amount;
            public long created_at;
            public HashMap<String, HashMap<String, OrderBean>> order_info;
            public int receive_target;
            public int record_id;
            public String service_fee;
            public int status;
            public String store_rid;

            public static class OrderBean {
                /**
                 * commission_price : 6.0
                 * created_at : 1534763366
                 */

                public double commission_price;
                public long created_at;
                public String orderName;
            }
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
