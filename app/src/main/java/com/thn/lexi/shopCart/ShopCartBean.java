package com.thn.lexi.shopCart;

import com.thn.lexi.beans.ProductBean;

import java.util.List;

public class ShopCartBean {

    /**
     * data : {"item_count":7,"items":[{"option":"其他","product":{"cover":"http://0.0.0.0:9000/_uploads/photos/1","cover_id":1,"id_code":"1","mode":"2 2","price":2,"product_name":"汽车","product_rid":"2","rid":"2","s_color":"2","s_model":"2","s_weight":2,"sale_price":2,"status":1,"stock_count":21611,"stock_quantity":21611,"store_name":"第一家"},"quantity":3,"rid":"2","store_id":2,"user_id":2}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * item_count : 7
         * items : [{"option":"其他","product":{"cover":"http://0.0.0.0:9000/_uploads/photos/1","cover_id":1,"id_code":"1","mode":"2 2","price":2,"product_name":"汽车","product_rid":"2","rid":"2","s_color":"2","s_model":"2","s_weight":2,"sale_price":2,"status":1,"stock_count":21611,"stock_quantity":21611,"store_name":"第一家"},"quantity":3,"rid":"2","store_id":2,"user_id":2}]
         */

        public int item_count;
        public List<ItemsBean> items;

        public static class ItemsBean {
            /**
             * option : 其他
             * product : {"cover":"http://0.0.0.0:9000/_uploads/photos/1","cover_id":1,"id_code":"1","mode":"2 2","price":2,"product_name":"汽车","product_rid":"2","rid":"2","s_color":"2","s_model":"2","s_weight":2,"sale_price":2,"status":1,"stock_count":21611,"stock_quantity":21611,"store_name":"第一家"}
             * quantity : 3
             * rid : 2
             * store_id : 2
             * user_id : 2
             */

            public String option;
            public ProductBean product;
            public int quantity;
            public String rid;
            public int store_id;
            public int user_id;
            public boolean isEdit;
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
