package com.lexivip.lexi.orderList;

import java.util.List;

public class MergeBean {
    /**
     * data : {"created_at":1539157265,"current_at":1541048780,"is_merge":true,"bonus_amount":5,"coupon_amount":0,"first_discount":0,"freight":1180,"user_pay_amount":10770,"reach_minus":5,"total_amount":9600,"order_list":[{"store_name":"第一家","total_quantity":30,"user_pay_amount":null},{"store_name":"第2家","total_quantity":30,"user_pay_amount":null}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * created_at : 1539157265
         * current_at : 1541048780
         * is_merge : true
         * bonus_amount : 5
         * coupon_amount : 0
         * first_discount : 0
         * freight : 1180
         * user_pay_amount : 10770
         * reach_minus : 5
         * total_amount : 9600
         * order_list : [{"store_name":"第一家","total_quantity":30,"user_pay_amount":null},{"store_name":"第2家","total_quantity":30,"user_pay_amount":null}]
         */

        public int created_at;
        public int current_at;
        public boolean is_merge;
        public String bonus_amount;
        public String coupon_amount;
        public String first_discount;
        public String freight;
        public String user_pay_amount;
        public String reach_minus;
        public String total_amount;
        public List<OrderListBean> order_list;

        public static class OrderListBean {
            /**
             * store_name : 第一家
             * total_quantity : 30
             * user_pay_amount : null
             */

            public String store_name;
            public int total_quantity;
            public Object user_pay_amount;
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
