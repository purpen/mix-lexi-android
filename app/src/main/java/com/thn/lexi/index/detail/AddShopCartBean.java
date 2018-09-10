package com.thn.lexi.index.detail;

import com.thn.lexi.beans.ProductBean;

public class AddShopCartBean {

    /**
     * data : {"cart":{"option":null,"product":{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1535211627557.jpg","city":"深圳市","country":"中国","cover":"https://kg.erp.taihuoniao.com/lxServer/1535366553918.jpg","cover_id":74003,"delivery_city":"深圳市","delivery_country":"中国","delivery_country_id":1,"delivery_province":"广东","distribution_type":0,"fans_count":0,"is_free_postage":false,"mode":"默认 默认","price":58,"product_name":"动漫美少女战士同款耳环耳坠","product_rid":"8069187423","province":"广东","rid":"8746135928","s_color":"默认","s_model":"默认","s_weight":0,"sale_price":58,"status":1,"stock_count":1,"stock_quantity":1,"store_logo":"https://kg.erp.taihuoniao.com/lxServer/1535209620056.jpg","store_name":"草木素心手作","store_rid":"92379504","tag_line":"取材于草木，每一件都是带有大自然灵气的孤品","town":""},"quantity":1,"rid":"8746135928","store_id":16,"user_id":396},"item_count":1}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * cart : {"option":null,"product":{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1535211627557.jpg","city":"深圳市","country":"中国","cover":"https://kg.erp.taihuoniao.com/lxServer/1535366553918.jpg","cover_id":74003,"delivery_city":"深圳市","delivery_country":"中国","delivery_country_id":1,"delivery_province":"广东","distribution_type":0,"fans_count":0,"is_free_postage":false,"mode":"默认 默认","price":58,"product_name":"动漫美少女战士同款耳环耳坠","product_rid":"8069187423","province":"广东","rid":"8746135928","s_color":"默认","s_model":"默认","s_weight":0,"sale_price":58,"status":1,"stock_count":1,"stock_quantity":1,"store_logo":"https://kg.erp.taihuoniao.com/lxServer/1535209620056.jpg","store_name":"草木素心手作","store_rid":"92379504","tag_line":"取材于草木，每一件都是带有大自然灵气的孤品","town":""},"quantity":1,"rid":"8746135928","store_id":16,"user_id":396}
         * item_count : 1
         */

        public CartBean cart;
        public int item_count;

        public static class CartBean {
            /**
             * option : null
             * product : {"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1535211627557.jpg","city":"深圳市","country":"中国","cover":"https://kg.erp.taihuoniao.com/lxServer/1535366553918.jpg","cover_id":74003,"delivery_city":"深圳市","delivery_country":"中国","delivery_country_id":1,"delivery_province":"广东","distribution_type":0,"fans_count":0,"is_free_postage":false,"mode":"默认 默认","price":58,"product_name":"动漫美少女战士同款耳环耳坠","product_rid":"8069187423","province":"广东","rid":"8746135928","s_color":"默认","s_model":"默认","s_weight":0,"sale_price":58,"status":1,"stock_count":1,"stock_quantity":1,"store_logo":"https://kg.erp.taihuoniao.com/lxServer/1535209620056.jpg","store_name":"草木素心手作","store_rid":"92379504","tag_line":"取材于草木，每一件都是带有大自然灵气的孤品","town":""}
             * quantity : 1
             * rid : 8746135928
             * store_id : 16
             * user_id : 396
             */

            public ProductBean product;
            public int quantity;
            public String rid;
            public int store_id;
            public int user_id;


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
