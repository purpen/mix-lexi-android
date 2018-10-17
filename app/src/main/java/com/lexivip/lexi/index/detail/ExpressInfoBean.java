package com.lexivip.lexi.index.detail;

import java.util.List;

public class ExpressInfoBean {

    /**
     * data : {"name":"运费模板3","pricing_method":1,"rid":"Ft726918503","update_at":1528707342,"items":[{"continuous_amount":20,"continuous_item":1,"continuous_weight":0,"express_code":"YTO","express_id":4,"express_name":"圆通","first_amount":10,"first_item":1,"first_weight":0,"is_default":true,"max_days":5,"min_days":3,"place_items":[{"continuous_amount":20,"continuous_item":1,"continuous_weight":0,"first_amount":10,"first_item":1,"first_weight":0,"is_default":false,"places":[{"area_scope":1,"place_name":"北京","place_oid":1},{"area_scope":1,"place_name":"天津","place_oid":2}],"rid":"Fi153042768"}],"rid":"Fi618937502"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * name : 运费模板3
         * pricing_method : 1
         * rid : Ft726918503
         * update_at : 1528707342
         * items : [{"continuous_amount":20,"continuous_item":1,"continuous_weight":0,"express_code":"YTO","express_id":4,"express_name":"圆通","first_amount":10,"first_item":1,"first_weight":0,"is_default":true,"max_days":5,"min_days":3,"place_items":[{"continuous_amount":20,"continuous_item":1,"continuous_weight":0,"first_amount":10,"first_item":1,"first_weight":0,"is_default":false,"places":[{"area_scope":1,"place_name":"北京","place_oid":1},{"area_scope":1,"place_name":"天津","place_oid":2}],"rid":"Fi153042768"}],"rid":"Fi618937502"}]
         */

        public String name;
        public int pricing_method;
        public String rid;
        public int update_at;
        public List<ItemsBean> items;

        public static class ItemsBean {
            /**
             * continuous_amount : 20
             * continuous_item : 1
             * continuous_weight : 0
             * express_code : YTO
             * express_id : 4
             * express_name : 圆通
             * first_amount : 10
             * first_item : 1
             * first_weight : 0
             * is_default : true
             * max_days : 5
             * min_days : 3
             * place_items : [{"continuous_amount":20,"continuous_item":1,"continuous_weight":0,"first_amount":10,"first_item":1,"first_weight":0,"is_default":false,"places":[{"area_scope":1,"place_name":"北京","place_oid":1},{"area_scope":1,"place_name":"天津","place_oid":2}],"rid":"Fi153042768"}]
             * rid : Fi618937502
             */

            public double continuous_amount;
            public int continuous_item;
            public double continuous_weight;
            public String express_code;
            public int express_id;
            public String express_name;
            public int first_amount;
            public int first_item;
            public double first_weight;
            public boolean is_default;
            public int max_days;
            public int min_days;
            public String rid;
            public List<PlaceItemsBean> place_items;

            public static class PlaceItemsBean {
                /**
                 * continuous_amount : 20
                 * continuous_item : 1
                 * continuous_weight : 0
                 * first_amount : 10
                 * first_item : 1
                 * first_weight : 0
                 * is_default : false
                 * places : [{"area_scope":1,"place_name":"北京","place_oid":1},{"area_scope":1,"place_name":"天津","place_oid":2}]
                 * rid : Fi153042768
                 */

                public double continuous_amount;
                public int continuous_item;
                public double continuous_weight;
                public int first_amount;
                public int first_item;
                public double first_weight;
                public boolean is_default;
                public String rid;
                public List<PlacesBean> places;

                public static class PlacesBean {
                    /**
                     * area_scope : 1
                     * place_name : 北京
                     * place_oid : 1
                     */

                    public int area_scope;
                    public String place_name;
                    public int place_oid;
                }
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
