package com.lexivip.lexi.payUtil;

import java.util.List;

public class PayWXBean {
    /**
     * data : {"_asdict":{},"appid":"appid","current_at":1539245473,"created_at":1539245473,"is_merge":true,"mch_id":"mch_id","nonce_str":"nonce_str","order_rid":"D18070316803529","prepay_id":"prepay_id","result_code":"SUCCESS","return_code":"SUCCESS","return_msg":"OK","sign":"sign","trade_type":"APP","order_list":[{"store_name":"第一家","total_quantity":30,"user_pay_amount":1184.7},{"store_name":"第2家","total_quantity":30,"user_pay_amount":9585.3}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * _asdict : {}
         * appid : appid
         * current_at : 1539245473
         * created_at : 1539245473
         * is_merge : true
         * mch_id : mch_id
         * nonce_str : nonce_str
         * order_rid : D18070316803529
         * prepay_id : prepay_id
         * result_code : SUCCESS
         * return_code : SUCCESS
         * return_msg : OK
         * sign : sign
         * trade_type : APP
         * order_list : [{"store_name":"第一家","total_quantity":30,"user_pay_amount":1184.7},{"store_name":"第2家","total_quantity":30,"user_pay_amount":9585.3}]
         */

        public AsdictBean _asdict;
        public String appid;
        public String current_at;
        public String created_at;
        public boolean is_merge;
        public String mch_id;
        public String nonce_str;
        public String order_rid;
        public String prepay_id;
        public String result_code;
        public String return_code;
        public String return_msg;
        public String sign;
        public String trade_type;
        public String order_string;
        public List<OrderListBean> order_list;

        public static class AsdictBean {
        }

        public static class OrderListBean {
            /**
             * store_name : 第一家
             * total_quantity : 30
             * user_pay_amount : 1184.7
             */

            public String store_name;
            public int total_quantity;
            public double user_pay_amount;
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
