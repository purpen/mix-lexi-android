package com.thn.lexi.net;

public class NetStatusBean {

    /**
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
