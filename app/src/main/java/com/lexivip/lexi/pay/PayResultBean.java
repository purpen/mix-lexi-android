package com.lexivip.lexi.pay;

import java.util.List;

public class PayResultBean {

    /**
     * data : {"orders":[{"buyer_address":"青年路","buyer_city":"淄博","buyer_country":"中国","buyer_name":"ZT-2","buyer_phone":"13260180689","buyer_province":"山东","buyer_remark":"哈哈哈","buyer_tel":"13260180689","buyer_zipcode":"255300","coupon_amount":0,"created_at":1530608616,"current_time":1533813725,"customer_order_id":null,"discount_amount":0,"first_discount":0,"freight":14,"official_order_id":null,"outside_target_id":"D18070316803529","pay_amount":17,"user_pay_amount":17,"official_bonus_amount":0,"reach_minus":0,"distributed":false,"remark":null,"rid":"D18070316803529","ship_mode":1,"status":5,"user_order_status":1,"store":{"store_logo":{"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"},"store_name":"第一家","store_rid":"2"},"total_amount":3,"total_quantity":3,"blessing_utterance":"嘿嘿嘿","payed_at":12321312,"received_at":12321312,"is_many_express":false,"items":[{"bgcover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","city":"","commission_price":null,"commission_rate":10,"country":"","cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","cover_id":1,"deal_price":30,"delivery_city":"北京","delivery_country":"","delivery_country_id":null,"delivery_province":"北京","distribution_type":1,"express":4,"express_at":0,"express_no":null,"express_code":"ZJS","express_name":"宅急送","fans_count":1,"freight":590,"freight_name":"运费模板1000","mode":"1 1","order_sku_commission_price":6,"order_sku_commission_rate":5,"price":33,"product_name":"摩托","product_rid":"3","province":"","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":30,"stock_count":32033,"stock_quantity":32033,"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3","tag_line":"1","town":""}]}],"buyer_city":"淄博","buyer_name":"张","buyer_province":"山东","buyer_address":"青年路","buyer_town":"周村","buyer_tel":"13260180689","buyer_zipcode":"255300","coupon_amount":0,"first_discount":959,"bonus_amount":4.23,"freight":590,"reach_minus":0,"total_amount":9000,"user_pay_amount":8626.77,"pay_status":"SUCCESS"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * orders : [{"buyer_address":"青年路","buyer_city":"淄博","buyer_country":"中国","buyer_name":"ZT-2","buyer_phone":"13260180689","buyer_province":"山东","buyer_remark":"哈哈哈","buyer_tel":"13260180689","buyer_zipcode":"255300","coupon_amount":0,"created_at":1530608616,"current_time":1533813725,"customer_order_id":null,"discount_amount":0,"first_discount":0,"freight":14,"official_order_id":null,"outside_target_id":"D18070316803529","pay_amount":17,"user_pay_amount":17,"official_bonus_amount":0,"reach_minus":0,"distributed":false,"remark":null,"rid":"D18070316803529","ship_mode":1,"status":5,"user_order_status":1,"store":{"store_logo":{"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"},"store_name":"第一家","store_rid":"2"},"total_amount":3,"total_quantity":3,"blessing_utterance":"嘿嘿嘿","payed_at":12321312,"received_at":12321312,"is_many_express":false,"items":[{"bgcover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","city":"","commission_price":null,"commission_rate":10,"country":"","cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","cover_id":1,"deal_price":30,"delivery_city":"北京","delivery_country":"","delivery_country_id":null,"delivery_province":"北京","distribution_type":1,"express":4,"express_at":0,"express_no":null,"express_code":"ZJS","express_name":"宅急送","fans_count":1,"freight":590,"freight_name":"运费模板1000","mode":"1 1","order_sku_commission_price":6,"order_sku_commission_rate":5,"price":33,"product_name":"摩托","product_rid":"3","province":"","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":30,"stock_count":32033,"stock_quantity":32033,"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3","tag_line":"1","town":""}]}]
         * buyer_city : 淄博
         * buyer_name : 张
         * buyer_province : 山东
         * buyer_address : 青年路
         * buyer_town : 周村
         * buyer_tel : 13260180689
         * buyer_zipcode : 255300
         * coupon_amount : 0
         * first_discount : 959
         * bonus_amount : 4.23
         * freight : 590
         * reach_minus : 0
         * total_amount : 9000
         * user_pay_amount : 8626.77
         * pay_status : SUCCESS
         */

        public String buyer_city;
        public String buyer_name;
        public String buyer_province;
        public String buyer_address;
        public String buyer_town;
        public String buyer_tel;
        public String buyer_zipcode;
        public String coupon_amount;
        public String first_discount;
        public double bonus_amount;
        public double freight;
        public String reach_minus;
        public double total_amount;
        public double user_pay_amount;
        public String pay_status;
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
             * user_pay_amount : 17
             * official_bonus_amount : 0
             * reach_minus : 0
             * distributed : false
             * remark : null
             * rid : D18070316803529
             * ship_mode : 1
             * status : 5
             * user_order_status : 1
             * store : {"store_logo":{"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"},"store_name":"第一家","store_rid":"2"}
             * total_amount : 3
             * total_quantity : 3
             * blessing_utterance : 嘿嘿嘿
             * payed_at : 12321312
             * received_at : 12321312
             * is_many_express : false
             * items : [{"bgcover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","city":"","commission_price":null,"commission_rate":10,"country":"","cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","cover_id":1,"deal_price":30,"delivery_city":"北京","delivery_country":"","delivery_country_id":null,"delivery_province":"北京","distribution_type":1,"express":4,"express_at":0,"express_no":null,"express_code":"ZJS","express_name":"宅急送","fans_count":1,"freight":590,"freight_name":"运费模板1000","mode":"1 1","order_sku_commission_price":6,"order_sku_commission_rate":5,"price":33,"product_name":"摩托","product_rid":"3","province":"","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":30,"stock_count":32033,"stock_quantity":32033,"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3","tag_line":"1","town":""}]
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
            public String coupon_amount;
            public long created_at;
            public long current_time;
            public Object customer_order_id;
            public double discount_amount;
            public double first_discount;
            public double freight;
            public Object official_order_id;
            public String outside_target_id;
            public String pay_amount;
            public String user_pay_amount;
            public String official_bonus_amount;
            public String reach_minus;
            public boolean distributed;
            public Object remark;
            public String rid;
            public int ship_mode;
            public int status;
            public int user_order_status;
            public StoreBean store;
            public String total_amount;
            public String total_quantity;
            public String blessing_utterance;
            public long payed_at;
            public long received_at;
            public boolean is_many_express;
            public List<ItemsBean> items;

            public static class StoreBean {
                /**
                 * store_logo : {"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"}
                 * store_name : 第一家
                 * store_rid : 2
                 */

                public StoreLogoBean store_logo;
                public String store_name;
                public String store_rid;

                public static class StoreLogoBean {
                    /**
                     * created_at : 1
                     * filename : 1
                     * filepath : 180523/8f51855eedae984.jpg
                     * id : 1
                     * type : 1
                     * view_url : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
                     */

                    public long created_at;
                    public String filename;
                    public String filepath;
                    public int id;
                    public int type;
                    public String view_url;
                }
            }

            public static class ItemsBean {
                /**
                 * bgcover : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
                 * city :
                 * commission_price : null
                 * commission_rate : 10
                 * country :
                 * cover : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
                 * cover_id : 1
                 * deal_price : 30
                 * delivery_city : 北京
                 * delivery_country :
                 * delivery_country_id : null
                 * delivery_province : 北京
                 * distribution_type : 1
                 * express : 4
                 * express_at : 0
                 * express_no : null
                 * express_code : ZJS
                 * express_name : 宅急送
                 * fans_count : 1
                 * freight : 590
                 * freight_name : 运费模板1000
                 * mode : 1 1
                 * order_sku_commission_price : 6
                 * order_sku_commission_rate : 5
                 * price : 33
                 * product_name : 摩托
                 * product_rid : 3
                 * province :
                 * quantity : 3
                 * rid : 1
                 * s_color : 1
                 * s_model : 1
                 * s_weight : 1
                 * sale_price : 30
                 * stock_count : 32033
                 * stock_quantity : 32033
                 * store_logo : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
                 * store_name : 第2家
                 * store_rid : 3
                 * tag_line : 1
                 * town :
                 */

                public String bgcover;
                public String city;
                public double commission_price;
                public double commission_rate;
                public String country;
                public String cover;
                public int cover_id;
                public double deal_price;
                public String delivery_city;
                public String delivery_country;
                public Object delivery_country_id;
                public String delivery_province;
                public int distribution_type;
                public int express;
                public int express_at;
                public Object express_no;
                public String express_code;
                public String express_name;
                public int fans_count;
                public double freight;
                public String freight_name;
                public String mode;
                public double order_sku_commission_price;
                public double order_sku_commission_rate;
                public double price;
                public String product_name;
                public String product_rid;
                public String province;
                public int quantity;
                public String rid;
                public String s_color;
                public String s_model;
                public double s_weight;
                public double sale_price;
                public int stock_count;
                public int stock_quantity;
                public String store_logo;
                public String store_name;
                public String store_rid;
                public String tag_line;
                public String town;
                public int min_days;
                public int max_days;
                public String py_intro;
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
