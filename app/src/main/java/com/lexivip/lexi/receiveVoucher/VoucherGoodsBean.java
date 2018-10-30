package com.lexivip.lexi.receiveVoucher;

import java.util.List;

public class VoucherGoodsBean {
    /**
     * data : {"coupons":[{"amount":"4","coupon_code":"UQNJSFUAZOX","is_recommend":true,"min_amount":"4","product_amount":"10","product_coupon_amount":"6","product_cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","product_name":"摩托","product_rid":"1","store_rid":"3","is_grant":false,"surplus_count":1000}],"next":null,"prev":null}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * coupons : [{"amount":"4","coupon_code":"UQNJSFUAZOX","is_recommend":true,"min_amount":"4","product_amount":"10","product_coupon_amount":"6","product_cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","product_name":"摩托","product_rid":"1","store_rid":"3","is_grant":false,"surplus_count":1000}]
         * next : null
         * prev : null
         */

        public Object next;
        public Object prev;
        public List<CouponsBean> coupons;

        public static class CouponsBean {
            /**
             * amount : 4
             * coupon_code : UQNJSFUAZOX
             * is_recommend : true
             * min_amount : 4
             * product_amount : 10
             * product_coupon_amount : 6
             * product_cover : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
             * product_name : 摩托
             * product_rid : 1
             * store_rid : 3
             * is_grant : false
             * surplus_count : 1000
             */

            public String amount;
            public String coupon_code;
            public boolean is_recommend;
            public String min_amount;
            public String product_amount;
            public String product_coupon_amount;
            public String product_cover;
            public String product_name;
            public String product_rid;
            public String store_rid;
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
