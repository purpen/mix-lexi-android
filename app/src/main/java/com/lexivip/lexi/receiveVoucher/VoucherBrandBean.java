package com.lexivip.lexi.receiveVoucher;

import java.util.List;

public class VoucherBrandBean {
    /**
     * data : {"coupons":[{"amount":"4","coupon_code":"UMBSHQGIVDE","is_recommend":true,"min_amount":"290","product_sku":[{"product_amount":"300","product_coupon_amount":"296","product_cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","product_name":"自行车","product_rid":"3"}],"store_bgcover":"https://s3.moebeast.com/20180910/5037FninYcPJRCGRAghbGQvQM9cD45z3.jpg","store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3"}],"next":null,"prev":null}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * coupons : [{"amount":"4","coupon_code":"UMBSHQGIVDE","is_recommend":true,"min_amount":"290","product_sku":[{"product_amount":"300","product_coupon_amount":"296","product_cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","product_name":"自行车","product_rid":"3"}],"store_bgcover":"https://s3.moebeast.com/20180910/5037FninYcPJRCGRAghbGQvQM9cD45z3.jpg","store_logo":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","store_name":"第2家","store_rid":"3"}]
         * next : null
         * prev : null
         */

        public Object next;
        public Object prev;
        public List<CouponsBean> coupons;

        public static class CouponsBean {
            /**
             * amount : 4
             * coupon_code : UMBSHQGIVDE
             * is_recommend : true
             * min_amount : 290
             * product_sku : [{"product_amount":"300","product_coupon_amount":"296","product_cover":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg","product_name":"自行车","product_rid":"3"}]
             * store_bgcover : https://s3.moebeast.com/20180910/5037FninYcPJRCGRAghbGQvQM9cD45z3.jpg
             * store_logo : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
             * store_name : 第2家
             * store_rid : 3
             */

            public String amount;
            public String coupon_code;
            public boolean is_recommend;
            public String min_amount;
            public String store_bgcover;
            public String store_logo;
            public String store_name;
            public String store_rid;
            public List<ProductSkuBean> product_sku;

            public static class ProductSkuBean {
                /**
                 * product_amount : 300
                 * product_coupon_amount : 296
                 * product_cover : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
                 * product_name : 自行车
                 * product_rid : 3
                 */

                public String product_amount;
                public String product_coupon_amount;
                public String product_cover;
                public String product_name;
                public String product_rid;
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
