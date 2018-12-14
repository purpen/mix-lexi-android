package com.lexivip.lexi.lifeShop;

import java.util.List;

public class MyFriendBean {
    /**
     * data : {"count":1,"friends":[{"amount":0,"phases":1,"user_logo":"https://s3.lexivip.com/avatar-8457984.png","user_name":"15935823397","user_sn":"15847260931"}],"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 1
         * friends : [{"amount":0,"phases":1,"user_logo":"https://s3.lexivip.com/avatar-8457984.png","user_name":"15935823397","user_sn":"15847260931"}]
         * next : false
         * prev : false
         */

        public int count;
        public boolean next;
        public boolean prev;
        public List<FriendsBean> friends;

        public static class FriendsBean {
            /**
             * amount : 0
             * phases : 1
             * user_logo : https://s3.lexivip.com/avatar-8457984.png
             * user_name : 15935823397
             * user_sn : 15847260931
             */

            public String amount;
            public int phases;
            public String user_logo;
            public String user_name;
            public String user_sn;
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
