package com.lexivip.lexi.user.login;

import java.util.List;

public class UserProfileBean {

    /**
     * data : {"is_small_b":true,"profile":{"about_me":"好人","area":"","area_id":0,"areacode":"+86","avatar":"http://0.0.0.0:9000/_uploads/photos/FlHKgXPzqwjPC7pD5Z_SfdL0R8hE","avatar_id":375,"city":"北京","city_id":1,"country":"中国","country_id":1,"created_at":1532055457,"date":"2018-07-31","description":null,"email":"13001179400","gender":0,"is_distributor":true,"last_seen":1534065116,"mail":"4568794@qq.com","master_uid":2,"mobile":"13001179477","phone":"","province":"北京","province_id":1,"street_address":"天安门","uid":"17048395612","user_areacode":["13001179477"],"username":"wdd"},"store_rid":"78936740"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * is_small_b : true
         * profile : {"about_me":"好人","area":"","area_id":0,"areacode":"+86","avatar":"http://0.0.0.0:9000/_uploads/photos/FlHKgXPzqwjPC7pD5Z_SfdL0R8hE","avatar_id":375,"city":"北京","city_id":1,"country":"中国","country_id":1,"created_at":1532055457,"date":"2018-07-31","description":null,"email":"13001179400","gender":0,"is_distributor":true,"last_seen":1534065116,"mail":"4568794@qq.com","master_uid":2,"mobile":"13001179477","phone":"","province":"北京","province_id":1,"street_address":"天安门","uid":"17048395612","user_areacode":["13001179477"],"username":"wdd"}
         * store_rid : 78936740
         */

        public boolean is_small_b;
        public ProfileBean profile;
        public String store_rid;

        public static class ProfileBean {
            /**
             * about_me : 好人
             * area :
             * area_id : 0
             * areacode : +86
             * avatar : http://0.0.0.0:9000/_uploads/photos/FlHKgXPzqwjPC7pD5Z_SfdL0R8hE
             * avatar_id : 375
             * city : 北京
             * city_id : 1
             * country : 中国
             * country_id : 1
             * created_at : 1532055457
             * date : 2018-07-31
             * description : null
             * email : 13001179400
             * gender : 0
             * is_distributor : true
             * last_seen : 1534065116
             * mail : 4568794@qq.com
             * master_uid : 2
             * mobile : 13001179477
             * phone :
             * province : 北京
             * province_id : 1
             * street_address : 天安门
             * uid : 17048395612
             * user_areacode : ["13001179477"]
             * username : wdd
             */

            public String about_me;
            public String area;
            public int area_id;
            public String areacode;
            public String avatar;
            public int avatar_id;
            public String city;
            public int city_id;
            public String country;
            public int country_id;
            public int created_at;
            public String date;
            public String email;
            public int gender;
            public boolean is_distributor;
            public int last_seen;
            public String mail;
            public int master_uid;
            public String mobile;
            public String phone;
            public String province;
            public int province_id;
            public String street_address;
            public String uid;
            public String username;
            public List<String> user_areacode;
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
