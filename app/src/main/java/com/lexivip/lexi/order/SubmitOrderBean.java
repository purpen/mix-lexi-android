package com.lexivip.lexi.order;

import java.util.List;

public class SubmitOrderBean {

    /**
     * data : {"actual_payment":17,"bonus_amount":0,"order_rid":"D18090562079514","orders":[{"buyer_address":"青年路","buyer_city":"淄博","buyer_country":"中国","buyer_name":"ZT-2","buyer_phone":"13260180689","buyer_province":"山东","buyer_remark":"哈哈哈","buyer_tel":"13260180689","buyer_zipcode":"255300","coupon_amount":0,"created_at":1530608616,"current_time":1533813725,"customer_order_id":null,"discount_amount":0,"first_discount":0,"freight":14,"official_order_id":null,"outside_target_id":"D18070316803529","pay_amount":17,"reach_minus":0,"distributed":false,"remark":null,"rid":"D18070316803529","ship_mode":1,"status":5,"store":{"store_logo":{"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"},"store_name":"第一家","store_rid":"2"},"total_amount":3,"total_quantity":3,"blessing_utterance":"嘿嘿嘿","payed_at":12321312,"received_at":12321312,"is_many_express":false,"items":[{"order_sku_commission_price":6,"order_sku_commission_rate":5,"commission_price":null,"commission_rate":5,"cover":"http://0.0.0.0:9000/_uploads/photos/1","deal_price":1,"express":4,"express_code":"YTO","express_name":"圆通","express_at":0,"express_no":null,"id_code":"1","mode":"1 1","price":1,"product_name":"摩托","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":1,"stock_count":11020}]}]}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * actual_payment : 17
         * bonus_amount : 0
         * order_rid : D18090562079514
         * orders : [{"buyer_address":"青年路","buyer_city":"淄博","buyer_country":"中国","buyer_name":"ZT-2","buyer_phone":"13260180689","buyer_province":"山东","buyer_remark":"哈哈哈","buyer_tel":"13260180689","buyer_zipcode":"255300","coupon_amount":0,"created_at":1530608616,"current_time":1533813725,"customer_order_id":null,"discount_amount":0,"first_discount":0,"freight":14,"official_order_id":null,"outside_target_id":"D18070316803529","pay_amount":17,"reach_minus":0,"distributed":false,"remark":null,"rid":"D18070316803529","ship_mode":1,"status":5,"store":{"store_logo":{"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"},"store_name":"第一家","store_rid":"2"},"total_amount":3,"total_quantity":3,"blessing_utterance":"嘿嘿嘿","payed_at":12321312,"received_at":12321312,"is_many_express":false,"items":[{"order_sku_commission_price":6,"order_sku_commission_rate":5,"commission_price":null,"commission_rate":5,"cover":"http://0.0.0.0:9000/_uploads/photos/1","deal_price":1,"express":4,"express_code":"YTO","express_name":"圆通","express_at":0,"express_no":null,"id_code":"1","mode":"1 1","price":1,"product_name":"摩托","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":1,"stock_count":11020}]}]
         */

        public int actual_payment;
        public int bonus_amount;
        public String order_rid;
        public List<OrdersBean> orders;

        public static class OrdersBean {
            /**
             * buyer_address : 青年路
             * buyer_city : 淄博
             * buyer_country : 中国
             * buyer_name : ZT-2
             * buyer_phone : 13260180689
             * buyer_province : 山东
             * buyer_remark : 哈哈哈
             * buyer_tel : 13260180689
             * buyer_zipcode : 255300
             * coupon_amount : 0
             * created_at : 1530608616
             * current_time : 1533813725
             * customer_order_id : null
             * discount_amount : 0
             * first_discount : 0
             * freight : 14
             * official_order_id : null
             * outside_target_id : D18070316803529
             * pay_amount : 17
             * reach_minus : 0
             * distributed : false
             * remark : null
             * rid : D18070316803529
             * ship_mode : 1
             * status : 5
             * store : {"store_logo":{"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"},"store_name":"第一家","store_rid":"2"}
             * total_amount : 3
             * total_quantity : 3
             * blessing_utterance : 嘿嘿嘿
             * payed_at : 12321312
             * received_at : 12321312
             * is_many_express : false
             * items : [{"order_sku_commission_price":6,"order_sku_commission_rate":5,"commission_price":null,"commission_rate":5,"cover":"http://0.0.0.0:9000/_uploads/photos/1","deal_price":1,"express":4,"express_code":"YTO","express_name":"圆通","express_at":0,"express_no":null,"id_code":"1","mode":"1 1","price":1,"product_name":"摩托","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":1,"stock_count":11020}]
             */

            public String buyer_address;
            public String buyer_city;
            public String buyer_country;
            public String buyer_name;
            public String buyer_phone;
            public String buyer_province;
            public String buyer_remark;
            public String buyer_tel;
            public String buyer_zipcode;
            public int coupon_amount;
            public int created_at;
            public int current_time;
            public Object customer_order_id;
            public int discount_amount;
            public int first_discount;
            public int freight;
            public Object official_order_id;
            public String outside_target_id;
            public int pay_amount;
            public int reach_minus;
            public boolean distributed;
            public Object remark;
            public String rid;
            public int ship_mode;
            public int status;
            public StoreBean store;
            public int total_amount;
            public int total_quantity;
            public String blessing_utterance;
            public int payed_at;
            public int received_at;
            public boolean is_many_express;
            public List<ItemsBean> items;

            public static class StoreBean {
                /**
                 * store_logo : {"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"}
                 * store_name : 第一家
                 * store_rid : 2
                 */

//                public StoreLogoBean store_logo;
                public String store_name;
                public String store_rid;

//                public static class StoreLogoBean {
//                    /**
//                     * created_at : 1
//                     * filename : 1
//                     * filepath : 180523/8f51855eedae984.jpg
//                     * id : 1
//                     * type : 1
//                     * view_url : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
//                     */
//
//                    public int created_at;
//                    public String filename;
//                    public String filepath;
//                    public int id;
//                    public int type;
//                    public String view_url;
//                }
            }

            public static class ItemsBean {
                /**
                 * order_sku_commission_price : 6
                 * order_sku_commission_rate : 5
                 * commission_price : null
                 * commission_rate : 5
                 * cover : http://0.0.0.0:9000/_uploads/photos/1
                 * deal_price : 1
                 * express : 4
                 * express_code : YTO
                 * express_name : 圆通
                 * express_at : 0
                 * express_no : null
                 * id_code : 1
                 * mode : 1 1
                 * price : 1
                 * product_name : 摩托
                 * quantity : 3
                 * rid : 1
                 * s_color : 1
                 * s_model : 1
                 * s_weight : 1
                 * sale_price : 1
                 * stock_count : 11020
                 */

                public int order_sku_commission_price;
                public int order_sku_commission_rate;
                public Object commission_price;
                public int commission_rate;
                public String cover;
                public int deal_price;
                public int express;
                public String express_code;
                public String express_name;
                public int express_at;
                public Object express_no;
                public String id_code;
                public String mode;
                public int price;
                public String product_name;
                public int quantity;
                public String rid;
                public String s_color;
                public String s_model;
                public double s_weight;
                public int sale_price;
                public int stock_count;
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