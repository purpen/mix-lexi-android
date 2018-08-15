package com.thn.lexi.index.detail;

import java.util.List;

public class GoodsInfoBean {

    /**
     * data : {"brand":null,"commission_price":2.22,"commission_rate":null,"cover":"https://kg.erp.taihuoniao.com/20180628/0708FpYj9tYJgqQmgVZOxmZk-rhCB5I-.jpg","custom_details":"呃呃","features":"123呵呵呵","id_code":"测试白1","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_made_holiday":true,"is_proprietary":true,"is_sold_out":false,"like_count":0,"made_cycle":5666,"max_price":222,"max_sale_price":333,"min_price":222,"min_sale_price":333,"modes":["444 绿色"],"name":"分类","published_at":0,"real_price":222,"real_sale_price":333,"rid":"8431597026","status":1,"sticked":false,"total_stock":111}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * brand : null
         * commission_price : 2.22
         * commission_rate : null
         * cover : https://kg.erp.taihuoniao.com/20180628/0708FpYj9tYJgqQmgVZOxmZk-rhCB5I-.jpg
         * custom_details : 呃呃
         * features : 123呵呵呵
         * id_code : 测试白1
         * is_custom_made : true
         * is_custom_service : true
         * is_distributed : true
         * is_free_postage : false
         * is_made_holiday : true
         * is_proprietary : true
         * is_sold_out : false
         * like_count : 0
         * made_cycle : 5666
         * max_price : 222
         * max_sale_price : 333
         * min_price : 222
         * min_sale_price : 333
         * modes : ["444 绿色"]
         * name : 分类
         * published_at : 0
         * real_price : 222
         * real_sale_price : 333
         * rid : 8431597026
         * status : 1
         * sticked : false
         * total_stock : 111
         */

        public double commission_price;
        public String cover;
        public String custom_details;
        public String features;
        public String id_code;
        public boolean is_custom_made;
        public boolean is_custom_service;
        public boolean is_distributed;
        public boolean is_free_postage;
        public boolean is_made_holiday;
        public boolean is_proprietary;
        public boolean is_sold_out;
        public String like_count;
        public String made_cycle;
        public String max_price;
        public String max_sale_price;
        public String min_price;
        public String min_sale_price;
        public String name;
        public String published_at;
        public String real_price;
        public String real_sale_price;
        public String rid;
        public int status;
        public boolean sticked;
        public int total_stock;
        public List<String> modes;
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
