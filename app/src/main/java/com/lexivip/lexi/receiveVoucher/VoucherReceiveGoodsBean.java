package com.lexivip.lexi.receiveVoucher;

import java.util.List;

public class VoucherReceiveGoodsBean {
    /**
     * data : {"coupon":{"amount":12,"code":"UOWASNJZDFB","count":9000,"created_at":1530081557,"days":7,"min_amount":200,"products":[{"name":"摩托","rid":"1"}],"type":2,"type_text":"部分商品可用"},"end_at":1530692898,"get_at":1530088098,"is_expired":false,"is_used":false,"order_rid":null,"used_at":0}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * coupon : {"amount":12,"code":"UOWASNJZDFB","count":9000,"created_at":1530081557,"days":7,"min_amount":200,"products":[{"name":"摩托","rid":"1"}],"type":2,"type_text":"部分商品可用"}
         * end_at : 1530692898
         * get_at : 1530088098
         * is_expired : false
         * is_used : false
         * order_rid : null
         * used_at : 0
         */

        public CouponBean coupon;
        public int end_at;
        public int get_at;
        public boolean is_expired;
        public boolean is_used;
        public Object order_rid;
        public int used_at;

        public static class CouponBean {
            /**
             * amount : 12
             * code : UOWASNJZDFB
             * count : 9000
             * created_at : 1530081557
             * days : 7
             * min_amount : 200
             * products : [{"name":"摩托","rid":"1"}]
             * type : 2
             * type_text : 部分商品可用
             */

            public String amount;
            public String code;
            public int count;
            public int created_at;
            public int days;
            public String min_amount;
            public int type;
            public String type_text;
            public List<ProductsBean> products;

            public static class ProductsBean {
                /**
                 * name : 摩托
                 * rid : 1
                 */

                public String name;
                public String rid;
            }
        }
    }

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
