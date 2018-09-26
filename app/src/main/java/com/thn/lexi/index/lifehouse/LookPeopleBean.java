package com.thn.lexi.index.lifehouse;

import com.thn.lexi.beans.UserBean;

import java.util.List;

public class LookPeopleBean {

    /**
     * data : {"count":2,"next_url":false,"prev_url":false,"total_page":1,"users":[{"about_me":"天道酬勤","area":"","area_id":0,"areacode":"+86","avatar":"https://kg.erp.taihuoniao.com/20180730/3210Fheo2XhNuVLxwVLlkb8Bc_BTvN9h.jpeg","avatar_id":3155,"city":"","city_id":0,"country":"","country_id":null,"created_at":1531570515,"date":"1993-01-01","description":null,"email":"18610320751","gender":1,"is_distributor":false,"last_seen":1531570515,"mail":"lexi@moebeast.com","master_uid":0,"mobile":"18610320751","phone":null,"province":"","province_id":0,"street_address":null,"uid":"11962435078","user_areacode":["18610320751"],"username":"乐喜七号"},{"about_me":null,"area":"","area_id":null,"areacode":"+86","avatar":"https://kg.erp.taihuoniao.com/20180726/3703FgkTUxcFE3_2DAXlTdi4rQMRU7IY.jpg","avatar_id":2877,"city":"","city_id":null,"country":"","country_id":null,"created_at":1530786369,"date":"1972-03-04","description":null,"email":"17600351560","gender":1,"is_distributor":false,"last_seen":1530945164,"mail":null,"master_uid":0,"mobile":"17600351560","phone":null,"province":"","province_id":null,"street_address":null,"uid":"17014638295","user_areacode":["17600351560"],"username":"大喜"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 2
         * next_url : false
         * prev_url : false
         * total_page : 1
         * users : [{"about_me":"天道酬勤","area":"","area_id":0,"areacode":"+86","avatar":"https://kg.erp.taihuoniao.com/20180730/3210Fheo2XhNuVLxwVLlkb8Bc_BTvN9h.jpeg","avatar_id":3155,"city":"","city_id":0,"country":"","country_id":null,"created_at":1531570515,"date":"1993-01-01","description":null,"email":"18610320751","gender":1,"is_distributor":false,"last_seen":1531570515,"mail":"lexi@moebeast.com","master_uid":0,"mobile":"18610320751","phone":null,"province":"","province_id":0,"street_address":null,"uid":"11962435078","user_areacode":["18610320751"],"username":"乐喜七号"},{"about_me":null,"area":"","area_id":null,"areacode":"+86","avatar":"https://kg.erp.taihuoniao.com/20180726/3703FgkTUxcFE3_2DAXlTdi4rQMRU7IY.jpg","avatar_id":2877,"city":"","city_id":null,"country":"","country_id":null,"created_at":1530786369,"date":"1972-03-04","description":null,"email":"17600351560","gender":1,"is_distributor":false,"last_seen":1530945164,"mail":null,"master_uid":0,"mobile":"17600351560","phone":null,"province":"","province_id":null,"street_address":null,"uid":"17014638295","user_areacode":["17600351560"],"username":"大喜"}]
         */

        public int count;
        public List<UserBean> users;
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
