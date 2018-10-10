package com.thn.lexi.lifeShop;

import java.util.List;

public class AccountDetailOrderBean {
    /**
     * data : {"blessing_utterance":"嘿嘿嘿","buyer_address":"青年路","buyer_city":"淄博","buyer_country":"中国","buyer_name":"张王","buyer_phone":"13260180689","buyer_province":"山东","buyer_remark":"哈哈哈","buyer_tel":"13260180689","buyer_zipcode":"255300","coupon_amount":0,"created_at":1533542675,"current_time":1534595020,"customer_order_id":"D18080665081342","discount_amount":1,"distributed":true,"first_discount":0,"freight":190,"items":[{"commission_price":10,"commission_rate":10,"cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","cover_id":1,"deal_price":1,"delivery_country":"","delivery_country_id":null,"express":4,"express_at":0,"express_code":"YTO","express_no":null,"freight":190,"mode":"1 1","price":3,"product_name":"自行车","product_rid":"3","quantity":10,"rid":"3","s_color":"1","s_model":"1","s_weight":1,"sale_price":3,"stock_count":32363,"stock_quantity":32363,"store_name":"第2家","store_rid":"3"}],"official_order_id":"D18080628045193","outside_target_id":"D18080663458120","pay_amount":199,"payed_at":0,"payment_slip":null,"reach_minus":1,"received_at":0,"refund_amount":0,"remark":null,"rid":"D18080663458120","ship_mode":1,"signed_at":null,"status":5,"store":{"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3"},"total_amount":10,"total_quantity":10}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * blessing_utterance : 嘿嘿嘿
         * buyer_address : 青年路
         * buyer_city : 淄博
         * buyer_country : 中国
         * buyer_name : 张王
         * buyer_phone : 13260180689
         * buyer_province : 山东
         * buyer_remark : 哈哈哈
         * buyer_tel : 13260180689
         * buyer_zipcode : 255300
         * coupon_amount : 0
         * created_at : 1533542675
         * current_time : 1534595020
         * customer_order_id : D18080665081342
         * discount_amount : 1
         * distributed : true
         * first_discount : 0
         * freight : 190
         * items : [{"commission_price":10,"commission_rate":10,"cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","cover_id":1,"deal_price":1,"delivery_country":"","delivery_country_id":null,"express":4,"express_at":0,"express_code":"YTO","express_no":null,"freight":190,"mode":"1 1","price":3,"product_name":"自行车","product_rid":"3","quantity":10,"rid":"3","s_color":"1","s_model":"1","s_weight":1,"sale_price":3,"stock_count":32363,"stock_quantity":32363,"store_name":"第2家","store_rid":"3"}]
         * official_order_id : D18080628045193
         * outside_target_id : D18080663458120
         * pay_amount : 199
         * payed_at : 0
         * payment_slip : null
         * reach_minus : 1
         * received_at : 0
         * refund_amount : 0
         * remark : null
         * rid : D18080663458120
         * ship_mode : 1
         * signed_at : null
         * status : 5
         * store : {"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3"}
         * total_amount : 10
         * total_quantity : 10
         */

        public String blessing_utterance;
        public String buyer_address;
        public String buyer_city;
        public String buyer_country;
        public String buyer_name;
        public String buyer_phone;
        public String buyer_province;
        public String buyer_remark;
        public String buyer_tel;
        public String buyer_zipcode;
        public double coupon_amount;
        public int created_at;
        public int current_time;
        public String customer_order_id;
        public double discount_amount;
        public boolean distributed;
        public double first_discount;
        public double freight;
        public String official_order_id;
        public String outside_target_id;
        public double pay_amount;
        public int payed_at;
        public Object payment_slip;
        public double reach_minus;
        public int received_at;
        public int refund_amount;
        public Object remark;
        public String rid;
        public int ship_mode;
        public Object signed_at;
        public int status;
        public StoreBean store;
        public double total_amount;
        public int total_quantity;
        public List<ItemsBean> items;

        public static class StoreBean {
            /**
             * store_logo : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
             * store_name : 第2家
             * store_rid : 3
             */

            public String store_logo;
            public String store_name;
            public String store_rid;
        }

        public static class ItemsBean {
            /**
             * commission_price : 10
             * commission_rate : 10
             * cover : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
             * cover_id : 1
             * deal_price : 1
             * delivery_country :
             * delivery_country_id : null
             * express : 4
             * express_at : 0
             * express_code : YTO
             * express_no : null
             * freight : 190
             * mode : 1 1
             * price : 3
             * product_name : 自行车
             * product_rid : 3
             * quantity : 10
             * rid : 3
             * s_color : 1
             * s_model : 1
             * s_weight : 1
             * sale_price : 3
             * stock_count : 32363
             * stock_quantity : 32363
             * store_name : 第2家
             * store_rid : 3
             */

            public double commission_price;
            public double commission_rate;
            public String cover;
            public int cover_id;
            public String deal_price;
            public String delivery_country;
            public Object delivery_country_id;
            public String express;
            public double order_sku_commission_price;
            public int express_at;
            public String express_code;
            public Object express_no;
            public String freight;
            public String mode;
            public String price;
            public String product_name;
            public String product_rid;
            public int quantity;
            public String rid;
            public String s_color;
            public String s_model;
            public int s_weight;
            public String sale_price;
            public String stock_count;
            public int stock_quantity;
            public String store_name;
            public String store_rid;
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
