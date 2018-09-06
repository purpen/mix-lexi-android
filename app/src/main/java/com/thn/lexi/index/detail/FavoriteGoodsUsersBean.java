package com.thn.lexi.index.detail;

import com.thn.lexi.beans.UserBean;

import java.util.List;

public class FavoriteGoodsUsersBean {

    /**
     * data : {"count":1,"next":false,"prev":false,"product_like_users":[{"about_me":"好人","area":"","area_id":0,"areacode":"+86","avatar":"http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png","avatar_id":1,"city":"北京","city_id":1,"country":"中国","country_id":1,"created_at":1532055457,"date":"2000-02-02","description":null,"email":"13001179400","followed_status":0,"gender":0,"is_distributor":false,"last_seen":1532055457,"mail":"4568794@qq.com","master_uid":2,"mobile":"+86-13645647894","phone":"","province":"北京","province_id":1,"street_address":"天安门","uid":"17048395612","user_areacode":["+86","13645647894"],"username":"张飞"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 1
         * next : false
         * prev : false
         * product_like_users : [{"about_me":"好人","area":"","area_id":0,"areacode":"+86","avatar":"http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png","avatar_id":1,"city":"北京","city_id":1,"country":"中国","country_id":1,"created_at":1532055457,"date":"2000-02-02","description":null,"email":"13001179400","followed_status":0,"gender":0,"is_distributor":false,"last_seen":1532055457,"mail":"4568794@qq.com","master_uid":2,"mobile":"+86-13645647894","phone":"","province":"北京","province_id":1,"street_address":"天安门","uid":"17048395612","user_areacode":["+86","13645647894"],"username":"张飞"}]
         */

        public int count;
        public List<UserBean> product_like_users;

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
