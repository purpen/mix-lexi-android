package com.thn.lexi.brandHouse;

public class AboutBrandHouseOwnerBean {
    /**
     * data : {"user_avatar":"http://0.0.0.0:9000/_uploads/photos/FlHKgXPzqwjPC7pD5Z_SfdL0R8hE","user_identity":1,"username":"wdd"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * user_avatar : http://0.0.0.0:9000/_uploads/photos/FlHKgXPzqwjPC7pD5Z_SfdL0R8hE
         * user_identity : 1
         * username : wdd
         */

        public String user_avatar;
        public int user_identity;
        public String username;
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
