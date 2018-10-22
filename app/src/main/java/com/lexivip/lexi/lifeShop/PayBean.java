package com.lexivip.lexi.lifeShop;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PayBean {
    /**
     * data : {"appId":"wxappId","nonceStr":"nonceStr","package":"prepay_id=package","signType":"signType","timeStamp":1539245473,"pay_sign":"pay_sign","prepay_id":"prepay_id","current_at":1539245473,"created_at":1539245473,"is_merge":true,"order_rid":"D18070316803529","order_list":[{"store_name":"第一家","total_quantity":30,"user_pay_amount":1184.7},{"store_name":"第2家","total_quantity":30,"user_pay_amount":9585.3}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * appId : wxappId
         * nonceStr : nonceStr
         * package : prepay_id=package
         * signType : signType
         * timeStamp : 1539245473
         * pay_sign : pay_sign
         * prepay_id : prepay_id
         * current_at : 1539245473
         * created_at : 1539245473
         * is_merge : true
         * order_rid : D18070316803529
         * order_list : [{"store_name":"第一家","total_quantity":30,"user_pay_amount":1184.7},{"store_name":"第2家","total_quantity":30,"user_pay_amount":9585.3}]
         */

        public String appId;
        public String nonceStr;
        @SerializedName("package")
        public String packageX;
        public String signType;
        public int timeStamp;
        public String pay_sign;
        public String prepay_id;
        public int current_at;
        public int created_at;
        public boolean is_merge;
        public String order_rid;
        public List<OrderListBean> order_list;

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
