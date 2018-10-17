package com.lexivip.lexi.search;

import com.lexivip.lexi.beans.UserBean;

import java.util.List;

public class SearchUserBean {

    /**
     * data : {"count":3,"next":false,"prev":false,"qk":"好","users":[{"about_me":null,"area":"","area_id":0,"areacode":"86","avatar":"https://kg.erp.taihuoniao.com/lxServer/user/user_p_1089.jpg","avatar_id":58755,"city":"","city_id":0,"country":"","country_id":null,"created_at":1536063131,"date":"1998-06-16","description":null,"email":"18710121060","follow_status":0,"gender":1,"is_distributor":false,"last_seen":1536063131,"last_store_rid":null,"mail":null,"master_uid":0,"mobile":"18710121060","phone":null,"province":"","province_id":0,"street_address":null,"town":"","town_id":0,"uid":"11796324058","user_areacode":["18710121060"],"user_like_counts":0,"username":"岁月婧好","wish_list_counts":0},{"about_me":null,"area":"","area_id":null,"areacode":null,"avatar":"https://kg.erp.taihuoniao.com/static/img/default-logo.png","avatar_id":null,"city":"","city_id":null,"country":"","country_id":null,"created_at":1525843037,"date":"","description":null,"email":"oO1Xq4hpvMnFZJwSnLWVxf74aOBE","follow_status":0,"gender":0,"is_distributor":null,"last_seen":1525843037,"last_store_rid":null,"mail":null,"master_uid":0,"mobile":null,"phone":null,"province":"","province_id":null,"street_address":null,"town":"","town_id":null,"uid":"12310785649","user_areacode":null,"user_like_counts":0,"username":"小好5493","wish_list_counts":0},{"about_me":null,"area":"","area_id":null,"areacode":null,"avatar":"https://kg.erp.taihuoniao.com/static/img/default-logo.png","avatar_id":null,"city":"","city_id":null,"country":"","country_id":null,"created_at":1528984720,"date":"","description":null,"email":"oO1Xq4tlJBCQNUt73xEEeD5aybBc","follow_status":0,"gender":1,"is_distributor":false,"last_seen":1528984720,"last_store_rid":null,"mail":null,"master_uid":0,"mobile":null,"phone":null,"province":"","province_id":null,"street_address":null,"town":"","town_id":null,"uid":"17506498213","user_areacode":null,"user_like_counts":0,"username":"好运来","wish_list_counts":0}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 3
         * next : false
         * prev : false
         * qk : 好
         * users : [{"about_me":null,"area":"","area_id":0,"areacode":"86","avatar":"https://kg.erp.taihuoniao.com/lxServer/user/user_p_1089.jpg","avatar_id":58755,"city":"","city_id":0,"country":"","country_id":null,"created_at":1536063131,"date":"1998-06-16","description":null,"email":"18710121060","follow_status":0,"gender":1,"is_distributor":false,"last_seen":1536063131,"last_store_rid":null,"mail":null,"master_uid":0,"mobile":"18710121060","phone":null,"province":"","province_id":0,"street_address":null,"town":"","town_id":0,"uid":"11796324058","user_areacode":["18710121060"],"user_like_counts":0,"username":"岁月婧好","wish_list_counts":0},{"about_me":null,"area":"","area_id":null,"areacode":null,"avatar":"https://kg.erp.taihuoniao.com/static/img/default-logo.png","avatar_id":null,"city":"","city_id":null,"country":"","country_id":null,"created_at":1525843037,"date":"","description":null,"email":"oO1Xq4hpvMnFZJwSnLWVxf74aOBE","follow_status":0,"gender":0,"is_distributor":null,"last_seen":1525843037,"last_store_rid":null,"mail":null,"master_uid":0,"mobile":null,"phone":null,"province":"","province_id":null,"street_address":null,"town":"","town_id":null,"uid":"12310785649","user_areacode":null,"user_like_counts":0,"username":"小好5493","wish_list_counts":0},{"about_me":null,"area":"","area_id":null,"areacode":null,"avatar":"https://kg.erp.taihuoniao.com/static/img/default-logo.png","avatar_id":null,"city":"","city_id":null,"country":"","country_id":null,"created_at":1528984720,"date":"","description":null,"email":"oO1Xq4tlJBCQNUt73xEEeD5aybBc","follow_status":0,"gender":1,"is_distributor":false,"last_seen":1528984720,"last_store_rid":null,"mail":null,"master_uid":0,"mobile":null,"phone":null,"province":"","province_id":null,"street_address":null,"town":"","town_id":null,"uid":"17506498213","user_areacode":null,"user_like_counts":0,"username":"好运来","wish_list_counts":0}]
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
