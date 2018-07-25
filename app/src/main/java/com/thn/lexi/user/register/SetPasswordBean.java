package com.thn.lexi.user.register;

public class SetPasswordBean {

    /**
     * data : {"areacode":"+86","email":"13001179411"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * areacode : +86
         * email : 13001179411
         */

        public String areacode;
        public String email;
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
