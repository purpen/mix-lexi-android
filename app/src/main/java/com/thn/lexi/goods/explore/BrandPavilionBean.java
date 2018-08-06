package com.thn.lexi.goods.explore;

import java.util.List;

public class BrandPavilionBean {

    /**
     * data : {"count":0,"next":false,"prev":false,"stores":[],"title":"特色品牌馆"}
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
         * stores : []
         * title : 特色品牌馆
         */

        public int count;
        public boolean next;
        public boolean prev;
        public String title;
        public List<String> stores;
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
