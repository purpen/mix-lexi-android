package com.thn.lexi.lifeShop;

import java.util.HashMap;
import java.util.List;

public class AccountStatementBean {

    /**
     * data : {"statements":{"7月":{"statements":[{"actual_account_amount":11,"actual_amount":11,"created_at":1534581237,"receive_target":1,"record_id":3,"service_fee":0,"status":2,"store_rid":"2"}],"total_amount":11}}}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * statements : {"7月":{"statements":[{"actual_account_amount":11,"actual_amount":11,"created_at":1534581237,"receive_target":1,"record_id":3,"service_fee":0,"status":2,"store_rid":"2"}],"total_amount":11}}
         */

        public HashMap<String, StatementsBeanX> statements;

        public static class StatementsBeanX {
            /**
             * statements : [{"actual_account_amount":11,"actual_amount":11,"created_at":1534581237,"receive_target":1,"record_id":3,"service_fee":0,"status":2,"store_rid":"2"}]
             * total_amount : 11.0
             */

            public double total_amount;
            public String name;
            public List<StatementsBean> statements;

            public static class StatementsBean {
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
                public long created_at;
                public int receive_target;
                public String record_id;
                public double service_fee;
                public int status;
                public String store_rid;
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
