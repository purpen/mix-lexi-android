package com.thn.lexi.brandHouse;

public class BrandHouseBean {

    /**
     * data : {"city":"北京","country":"中国","is_followed":false,"fans_count":0,"life_record_count":3,"logo":"http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png","bgcover":"","town":"","delivery_country":"","delivery_province":"","delivery_city":"","name":"","product_count":0,"province":"北京","rid":"97958360","tag_line":"sdjkf","created_at":12345612054}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * city : 北京
         * country : 中国
         * is_followed : false
         * fans_count : 0
         * life_record_count : 3
         * logo : http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png
         * bgcover :
         * town :
         * delivery_country :
         * delivery_province :
         * delivery_city :
         * name :
         * product_count : 0
         * province : 北京
         * rid : 97958360
         * tag_line : sdjkf
         * created_at : 12345612054
         */

        public String city;
        public String country;
        public boolean is_followed;
        public int fans_count;
        public int life_record_count;
        public String logo;
        public String bgcover;
        public String town;
        public String delivery_country;
        public String delivery_province;
        public String delivery_city;
        public String name;
        public int product_count;
        public String province;
        public String rid;
        public String tag_line;
        public long created_at;
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
