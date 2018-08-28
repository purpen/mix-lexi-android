package com.thn.lexi.mine;

import java.util.List;

public class GoodsLikeBean {

    /**
     * data : {"count":0,"next":false,"prev":false,"products":[]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 0
         * next : false
         * prev : false
         * products : []
         */

        public int count;
        public List<?> products;
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
