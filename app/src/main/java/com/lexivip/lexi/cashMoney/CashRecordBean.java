package com.lexivip.lexi.cashMoney;

import java.util.List;

public class CashRecordBean {
    /**
     * data : {"count":3,"next":false,"prev":false,"record_list":[{"actual_amount":4,"created_at":1544701407,"rid":"2342345","status":1},{"actual_amount":3,"created_at":1544701100,"rid":"2342342","status":1},{"actual_amount":5,"created_at":1544700900,"rid":"5634542","status":1}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 3
         * next : false
         * prev : false
         * record_list : [{"actual_amount":4,"created_at":1544701407,"rid":"2342345","status":1},{"actual_amount":3,"created_at":1544701100,"rid":"2342342","status":1},{"actual_amount":5,"created_at":1544700900,"rid":"5634542","status":1}]
         */

        public int count;
        public boolean next;
        public boolean prev;
        public List<RecordListBean> record_list;

        public static class RecordListBean {
            /**
             * actual_amount : 4
             * created_at : 1544701407
             * rid : 2342345
             * status : 1
             */

            public String actual_amount;
            public int created_at;
            public String rid;
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
