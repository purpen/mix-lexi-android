package com.thn.lexi.goods.bean;

public class FavoriteBean {

    /**
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public StatusBean status;
    public boolean success;

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
