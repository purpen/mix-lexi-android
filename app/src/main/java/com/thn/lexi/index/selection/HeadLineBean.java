package com.thn.lexi.index.selection;

import java.util.List;

public class HeadLineBean {


    /**
     * data : {"headlines":[{"event":3,"quantity":1,"time":1596757,"time_info":"","username":"13260180689"},{"event":3,"quantity":1,"time":1605684,"time_info":"","username":"13260180689"},{"event":3,"quantity":1,"time":1686454,"time_info":"","username":"13260180689"},{"event":3,"quantity":1,"time":1780437,"time_info":"","username":"13260180689"},{"event":3,"quantity":1,"time":1854020,"time_info":"","username":"13260180689"},{"event":3,"quantity":1,"time":2004246,"time_info":"","username":"13260180689"},{"event":3,"quantity":1,"time":2035432,"time_info":"","username":"13260180689"},{"event":3,"quantity":1,"time":2073776,"time_info":"","username":"13260180689"},{"event":3,"quantity":1,"time":2118273,"time_info":"","username":"13716171550"},{"event":3,"quantity":1,"time":2118725,"time_info":"","username":"13260180689"}]}
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
             * time : 1596757
             * time_info :
             * username : 13260180689
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
