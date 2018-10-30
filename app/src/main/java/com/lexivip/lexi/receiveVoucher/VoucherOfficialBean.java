package com.lexivip.lexi.receiveVoucher;

import java.util.List;

public class VoucherOfficialBean {
    /**
     * data : {"official_coupons":[{"amount":"5","code":"OUNVXIDAPGZ","count":2999,"created_at":1535433969,"end_date":1536422400,"min_amount":5,"pickup_count":0,"start_date":1535385600,"type_text":"满5减5元","use_count":0,"is_grant":false,"surplus_count":1000}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<OfficialCouponsBean> official_coupons;

        public static class OfficialCouponsBean {
            /**
             * amount : 5
             * code : OUNVXIDAPGZ
             * count : 2999
             * created_at : 1535433969
             * end_date : 1536422400
             * min_amount : 5
             * pickup_count : 0
             * start_date : 1535385600
             * type_text : 满5减5元
             * use_count : 0
             * is_grant : false
             * surplus_count : 1000
             */

            public String amount;
            public String code;
            public int count;
            public int created_at;
            public int end_date;
            public String min_amount;
            public int pickup_count;
            public int start_date;
            public String type_text;
            public int use_count;
            public boolean is_grant;
            public int surplus_count;
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
