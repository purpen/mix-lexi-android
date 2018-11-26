package com.lexivip.lexi.lifeShop;

import java.util.List;

public class TransactionOrderBean {
    /**
     * data : {"count":1,"finish_not_read":0,"pending_shipment_not_read":0,"shipment_not_read":0,"orders":[{"buyer_address":"青年路","buyer_city":"淄博","buyer_country":"中国","buyer_name":"ZT-2","buyer_phone":"13260180689","buyer_province":"山东","buyer_remark":null,"buyer_tel":"13260180689","buyer_zipcode":"255300","coupon_amount":0,"created_at":1530608616,"current_time":1533813725,"customer_order_id":null,"discount_amount":0,"first_discount":0,"freight":14,"is_many_express":false,"items":[{"bgcover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","city":"","commission_price":null,"commission_rate":10,"country":"","cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","cover_id":1,"deal_price":30,"delivery_city":"北京","delivery_country":"","delivery_country_id":null,"delivery_province":"北京","distribution_type":1,"express":4,"express_at":0,"express_no":null,"express_code":"ZJS","express_name":"宅急送","fans_count":1,"freight":590,"freight_name":"运费模板1000","mode":"1 1","order_sku_commission_price":6,"order_sku_commission_rate":5,"price":33,"product_name":"摩托","product_rid":"3","province":"","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":30,"stock_count":32033,"stock_quantity":32033,"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3","tag_line":"1","town":""}],"life_order_status":3,"official_order_id":null,"outside_target_id":"D18070316803529","pay_amount":17,"payed_at":1534231106,"payment_slip":null,"reach_minus":2,"received_at":1534232705,"refund_amount":0,"remark":null,"rid":"D18081490174623","ship_mode":1,"signed_at":0,"status":30,"store":{"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3"},"total_amount":30,"total_quantity":10}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 1
         * finish_not_read : 0
         * pending_shipment_not_read : 0
         * shipment_not_read : 0
         * orders : [{"buyer_address":"青年路","buyer_city":"淄博","buyer_country":"中国","buyer_name":"ZT-2","buyer_phone":"13260180689","buyer_province":"山东","buyer_remark":null,"buyer_tel":"13260180689","buyer_zipcode":"255300","coupon_amount":0,"created_at":1530608616,"current_time":1533813725,"customer_order_id":null,"discount_amount":0,"first_discount":0,"freight":14,"is_many_express":false,"items":[{"bgcover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","city":"","commission_price":null,"commission_rate":10,"country":"","cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","cover_id":1,"deal_price":30,"delivery_city":"北京","delivery_country":"","delivery_country_id":null,"delivery_province":"北京","distribution_type":1,"express":4,"express_at":0,"express_no":null,"express_code":"ZJS","express_name":"宅急送","fans_count":1,"freight":590,"freight_name":"运费模板1000","mode":"1 1","order_sku_commission_price":6,"order_sku_commission_rate":5,"price":33,"product_name":"摩托","product_rid":"3","province":"","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":30,"stock_count":32033,"stock_quantity":32033,"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3","tag_line":"1","town":""}],"life_order_status":3,"official_order_id":null,"outside_target_id":"D18070316803529","pay_amount":17,"payed_at":1534231106,"payment_slip":null,"reach_minus":2,"received_at":1534232705,"refund_amount":0,"remark":null,"rid":"D18081490174623","ship_mode":1,"signed_at":0,"status":30,"store":{"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3"},"total_amount":30,"total_quantity":10}]
         */

        public int count;
        public int finish_not_read;
        public int pending_shipment_not_read;
        public int shipment_not_read;
        public List<OrdersBean> orders;

        public static class OrdersBean {
            /**
             * buyer_address : 青年路
             * buyer_city : 淄博
             * buyer_country : 中国
             * buyer_name : ZT-2
             * buyer_phone : 13260180689
             * buyer_province : 山东
             * buyer_remark : null
             * buyer_tel : 13260180689
             * buyer_zipcode : 255300
             * coupon_amount : 0
             * created_at : 1530608616
             * current_time : 1533813725
             * customer_order_id : null
             * discount_amount : 0
             * first_discount : 0
             * freight : 14
             * is_many_express : false
             * items : [{"bgcover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","city":"","commission_price":null,"commission_rate":10,"country":"","cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","cover_id":1,"deal_price":30,"delivery_city":"北京","delivery_country":"","delivery_country_id":null,"delivery_province":"北京","distribution_type":1,"express":4,"express_at":0,"express_no":null,"express_code":"ZJS","express_name":"宅急送","fans_count":1,"freight":590,"freight_name":"运费模板1000","mode":"1 1","order_sku_commission_price":6,"order_sku_commission_rate":5,"price":33,"product_name":"摩托","product_rid":"3","province":"","quantity":3,"rid":"1","s_color":"1","s_model":"1","s_weight":1,"sale_price":30,"stock_count":32033,"stock_quantity":32033,"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3","tag_line":"1","town":""}]
             * life_order_status : 3
             * official_order_id : null
             * outside_target_id : D18070316803529
             * pay_amount : 17.0
             * payed_at : 1534231106
             * payment_slip : null
             * reach_minus : 2
             * received_at : 1534232705
             * refund_amount : 0
             * remark : null
             * rid : D18081490174623
             * ship_mode : 1
             * signed_at : 0
             * status : 30
             * store : {"store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3"}
             * total_amount : 30
             * total_quantity : 10
             */

            public String buyer_address;
            public String buyer_city;
            public String buyer_country;
            public String buyer_name;
            public String buyer_phone;
            public String buyer_province;
            public Object buyer_remark;
            public String buyer_tel;
            public String buyer_zipcode;
            public int coupon_amount;
            public int created_at;
            public int current_time;
            public Object customer_order_id;
            public int discount_amount;
            public int first_discount;
            public int freight;
            public boolean is_many_express;
            public int life_order_status;
            public Object official_order_id;
            public String outside_target_id;
            public double pay_amount;
            public int payed_at;
            public Object payment_slip;
            public int reach_minus;
            public int received_at;
            public int refund_amount;
            public Object remark;
            public String rid;
            public int ship_mode;
            public int signed_at;
            public int status;
            public StoreBean store;
            public int total_amount;
            public int total_quantity;
            public List<ItemsBean> items;
            public UserBean user_info;

            public static class UserBean{
                public String user_logo;
                public String user_name;
                public String user_sn;
            }

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
                 * bgcover : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
                 * city :
                 * commission_price : null
                 * commission_rate : 10
                 * country :
                 * cover : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
                 * cover_id : 1
                 * deal_price : 30.0
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
                public Object commission_price;
                public String commission_rate;
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
                public int freight;
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
                public int s_weight;
                public int sale_price;
                public int stock_count;
                public int stock_quantity;
                public String store_logo;
                public String store_name;
                public String store_rid;
                public String tag_line;
                public String town;
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
