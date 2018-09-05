package com.thn.lexi.index.detail;

import java.util.List;

public class GoodsAllSKUBean {

    /**
     * data : {"colors":[{"name":"深红色","valid":true}],"items":[{"commission_price":18.52,"commission_rate":1.5,"cover":"http://xxx/_uploads/photos/180302/496886303714e7e.jpg","mode":"深红色","price":"45.00","product_name":"测试供应商方式","rid":"118130473518","s_color":"深红色","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":8},{"commission_price":18.52,"commission_rate":1.5,"cover":"http://xxx/_uploads/photos/180202/a653192ecd0ec6f.jpg","mode":"","price":"45.00","product_name":"测试供应商方式","rid":"118150444328","s_color":"","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":0}],"modes":[]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<ColorsBean> colors;
        public List<ItemsBean> items;
        public List<ModesBean> modes;

        public static class ColorsBean {
            /**
             * name : 深红色
             * valid : true
             */

            public String name;
            public boolean valid;
            public boolean selected;
        }

        public static class ModesBean {
            /**
             * name : 规格
             * valid : true
             */

            public String name;
            public boolean valid;
            public boolean selected;
        }


        public static class ItemsBean {
            /**
             * commission_price : 18.52
             * commission_rate : 1.5
             * cover : http://xxx/_uploads/photos/180302/496886303714e7e.jpg
             * mode : 深红色
             * price : 45.00
             * product_name : 测试供应商方式
             * rid : 118130473518
             * s_color : 深红色
             * s_model :
             * s_weight : 0.00
             * sale_price : 0.00
             * stock_count : 8
             */

            public double commission_price;
            public double commission_rate;
            public String cover;
            public String mode;
            public double price;
            public String product_name;
            public String rid;
            public String s_color;
            public String s_model;
            public String s_weight;
            public double sale_price;
            public int stock_count;
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
