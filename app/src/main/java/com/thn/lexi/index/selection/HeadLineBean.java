package com.thn.lexi.index.selection;

import java.util.List;

public class HeadLineBean {


    /**
     * data : {"headlines":[{"event":3,"quantity":1,"time":19,"time_info":"小时前","username":"蘑菇蘑菇"},{"event":3,"quantity":1,"time":19,"time_info":"小时前","username":"蘑菇蘑菇"},{"event":3,"quantity":1,"time":19,"time_info":"小时前","username":"蘑菇蘑菇"},{"event":3,"quantity":1,"time":19,"time_info":"小时前","username":"蘑菇蘑菇"},{"event":3,"quantity":1,"time":23,"time_info":"小时前","username":"蘑菇蘑菇"},{"event":3,"quantity":1,"time":2,"time_info":"天前","username":"时尚小三郎"},{"event":3,"quantity":1,"time":2,"time_info":"天前","username":"时尚小三郎"},{"event":1,"time":2,"time_info":"天前","username":"时尚小三郎"},{"event":3,"quantity":1,"time":2,"time_info":"天前","username":"乱七八糟"},{"event":1,"time":2,"time_info":"天前","username":"乱七八糟"}]}
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
             * event : 3
             * quantity : 1
             * time : 19
             * time_info : 小时前
             * username : 蘑菇蘑菇
             */

            public int event;
            public int quantity;
            public int time;
            public String time_info;
            public String username;
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
