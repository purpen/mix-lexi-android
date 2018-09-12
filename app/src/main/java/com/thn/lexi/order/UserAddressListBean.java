package com.thn.lexi.order;

import java.util.List;

public class UserAddressListBean {

    /**
     * data : [{"area":null,"area_id":null,"city":"北京","city_id":1,"country_id":1,"first_name":"田","full_address":"北京北京大街","full_name":"田帅","is_default":false,"is_from_wx":false,"last_name":null,"mobile":"13278989898","phone":null,"province":"北京","province_id":1,"rid":"5254096781","street_address":"大街","street_address_two":null,"town":null,"town_id":0,"zipcode":null}]
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;
    public List<DataBean> data;

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }

    public static class DataBean {
        /**
         * area : null
         * area_id : null
         * city : 北京
         * city_id : 1
         * country_id : 1
         * first_name : 田
         * full_address : 北京北京大街
         * full_name : 田帅
         * is_default : false
         * is_from_wx : false
         * last_name : null
         * mobile : 13278989898
         * phone : null
         * province : 北京
         * province_id : 1
         * rid : 5254096781
         * street_address : 大街
         * street_address_two : null
         * town : null
         * town_id : 0
         * zipcode : null
         */

        public String area;
        public String area_id;
        public String city;
        public int city_id;
        public int country_id;
        public String first_name;
        public String full_address;
        public String full_name;
        public boolean is_default;
        public boolean is_from_wx;
        public String last_name;
        public String mobile;
        public String phone;
        public String province;
        public int province_id;
        public String rid;
        public String street_address;
        public String street_address_two;
        public String town;
        public int town_id;
        public String zipcode;
    }
}
