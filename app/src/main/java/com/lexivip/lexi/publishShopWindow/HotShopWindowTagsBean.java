package com.lexivip.lexi.publishShopWindow;

import java.util.List;

public class HotShopWindowTagsBean {

    /**
     * data : {"count":2,"keywords":[{"id":1,"name":"原创定制好物","numbers":"20000 参与人数","sort_order":1,"type":"1 标签类型： 0=无， 1=活动"},{"id":2,"name":"女孩","numbers":8888,"sort_order":2,"type":0}],"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 2
         * keywords : [{"id":1,"name":"原创定制好物","numbers":"20000 参与人数","sort_order":1,"type":"1 标签类型： 0=无， 1=活动"},{"id":2,"name":"女孩","numbers":8888,"sort_order":2,"type":0}]
         * next : false
         * prev : false
         */

        public int count;
        public List<KeywordsBean> keywords;

        public static class KeywordsBean {
            /**
             * id : 1
             * name : 原创定制好物
             * numbers : 20000 参与人数
             * sort_order : 1
             * type : 1 标签类型： 0=无， 1=活动
             */

            public int id;
            public String name;
            public int numbers;
            public int sort_order;
            public int type;
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
