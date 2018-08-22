package com.thn.lexi.discoverLifeAesthetics;

public class UserFocusState {

    /**
     * data : {"followed_status":2}
     * status : {"code":201,"message":"All created."}
     * success : true
     * 关注状态, 0:未关注; 1:已关注; 2:相互关注
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * followed_status : 2
         */

        public int followed_status;
    }

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
