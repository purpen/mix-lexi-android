package com.lexivip.lexi.publishShopWindow;

import java.util.List;

public class FuzzyMatchTagsBean {

    /**
     * data : {"count":2,"keywords":[{"name":"定制好设计","rid":30,"sort_order":1},{"name":"原创定制好物","rid":27,"sort_order":1}],"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 2
         * keywords : [{"name":"定制好设计","rid":30,"sort_order":1},{"name":"原创定制好物","rid":27,"sort_order":1}]
         * next : false
         * prev : false
         */

        public int count;
        public List<KeywordsBean> keywords;

        public static class KeywordsBean {
            /**
             * name : 定制好设计
             * rid : 30
             * sort_order : 1
             */

            public String name;
            public int rid;
            public int sort_order;
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
