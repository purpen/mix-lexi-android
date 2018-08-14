package com.thn.lexi.goods.selection;

import java.util.List;

public class HeadLineBean {

    /**
     * data : {"headlines":[{"hour":1,"line_text":"马赫的设计馆1小时售出45单","quantity":45,"username":"马赫","time":44},{"hour":1,"line_text":"冷洁的设计馆1小时售出15单","quantity":15,"username":"冷洁"},{"hour":1,"line_text":"马云的设计馆1小时售出50单","quantity":50,"username":"马云"},{"hour":1,"line_text":"东东的设计馆1小时售出6单","quantity":6,"username":"东东"},{"hour":3,"line_text":"豆豆的设计馆3小时售出11单","quantity":11,"username":"豆豆"},{"hour":3,"line_text":"高尚的设计馆3小时售出10单","quantity":10,"username":"高尚"},{"line_text":"小溪44小时前开通了自己的设计馆","time":44,"username":"小溪"},{"line_text":"乐喜七号45小时前开通了自己的设计馆","time":45,"username":"乐喜七号"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<HeadlinesBean> headlines;

        public static class HeadlinesBean {
            /**
             * hour : 1
             * line_text : 马赫的设计馆1小时售出45单
             * quantity : 45
             * username : 马赫
             * time : 44
             */

            public int hour;
            public String line_text;
            public int quantity;
            public String username;
            public int time;
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
