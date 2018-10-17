package com.lexivip.lexi.index.selection;

import java.util.List;

public class HeadLineBean {


    /**
     * data : {"headlines":[{"avatar":"https://s3.lexivip.com/avatar-1542206.png","event":1,"time":3,"time_info":"小时前","username":null,"quantity":1},{"avatar":"https://s3.lexivip.com/avatar-507736.png","event":3,"quantity":1,"time":20,"time_info":"小时前","username":"长的太美"},{"avatar":"https://s3.lexivip.com/avatar-4773051.png","event":1,"time":21,"time_info":"小时前","username":"总干事"},{"avatar":"https://s3.lexivip.com/avatar-7429355.png","event":1,"time":21,"time_info":"小时前","username":"小可吖"},{"avatar":"https://s3.lexivip.com/avatar-6297028.png","event":1,"time":21,"time_info":"小时前","username":"挑一"},{"avatar":"https://s3.lexivip.com/avatar-6706398.png","event":1,"time":21,"time_info":"小时前","username":"回头一眼"},{"avatar":"https://s3.lexivip.com/avatar-1637208.png","event":1,"time":21,"time_info":"小时前","username":"王小丫"},{"avatar":"https://s3.lexivip.com/avatar-6487602.png","event":2,"quantity":0,"time":21,"time_info":"小时前","username":"一方天地"},{"avatar":"https://s3.lexivip.com/avatar-8678958.png","event":1,"time":22,"time_info":"小时前","username":"第三方"},{"avatar":"https://s3.lexivip.com/wx_avatar/oDlWK5cCGtFsyOPDh0jZKnAKhUIw","event":3,"quantity":1,"time":3,"time_info":"天前","username":"purpen"},{"avatar":"https://s3.lexivip.com/avatar-983466.png","event":1,"time":3,"time_info":"天前","username":"暴力温柔"},{"avatar":"https://s3.lexivip.com/wx_avatar/oDlWK5R_rv-0K1okR35Kmla6OT6Q","event":1,"time":3,"time_info":"天前","username":"田小蹦"},{"avatar":"https://s3.lexivip.com/avatar-9174061.png","event":1,"time":4,"time_info":"天前","username":"老马来了"},{"avatar":"https://s3.lexivip.com/avatar-4081598.png","event":3,"quantity":1,"time":5,"time_info":"天前","username":"蘑菇蘑菇"},{"avatar":"https://s3.lexivip.com/avatar-4638473.png","event":1,"time":5,"time_info":"天前","username":"似懂非懂"},{"avatar":"https://s3.lexivip.com/avatar-903845.png","event":1,"time":6,"time_info":"天前","username":"独特之蕞"},{"avatar":"https://s3.lexivip.com/20181010/0705FikaB6I9ACc0v4-lS1q2R84tZ1H2.jpg","event":1,"time":6,"time_info":"天前","username":"时尚小三郎"},{"avatar":"https://s3.lexivip.com/20181010/2701Fk5rrJZq3vNK4sTRX8cN-7MYr1Eh.jpg","event":3,"quantity":1,"time":6,"time_info":"天前","username":"乱七八糟"},{"avatar":"https://s3.lexivip.com/20181010/2701Fk5rrJZq3vNK4sTRX8cN-7MYr1Eh.jpg","event":1,"time":6,"time_info":"天前","username":"乱七八糟"},{"avatar":"https://s3.lexivip.com/20181010/0548FqgHIkGQ2QST1xvY2ZhQrGCsf9LI.jpg","event":1,"time":6,"time_info":"天前","username":"可愛❤"},{"avatar":"https://s3.lexivip.com/20181010/4256FmPhtDgT0UBJ9NwglyuCj2b2WVih.jpg","event":1,"time":7,"time_info":"天前","username":"一晚上1"},{"avatar":"https://s3.lexivip.com/avatar-4081598.png","event":3,"quantity":1,"time":7,"time_info":"天前","username":"蘑菇蘑菇"},{"avatar":"https://s3.lexivip.com/avatar-6121074.png","event":4,"quantity":15,"time":1,"username":"李潮"},{"avatar":"https://s3.lexivip.com/avatar-3344060.png","event":4,"quantity":3,"time":2,"username":"拖拉小王子"},{"avatar":"https://s3.lexivip.com/avatar-482258.png","event":4,"quantity":8,"time":3,"username":"努力小生"},{"avatar":"https://s3.lexivip.com/avatar-7931818.png","event":4,"quantity":6,"time":3,"username":"微微一笑"},{"avatar":"https://s3.lexivip.com/avatar-9135467.png","event":4,"quantity":15,"time":2,"username":"叮叮"},{"avatar":"https://s3.lexivip.com/avatar-252516.png","event":4,"quantity":3,"time":2,"username":"李晓劲"},{"avatar":"https://s3.lexivip.com/avatar-9016895.png","event":4,"quantity":7,"time":3,"username":"小欣"},{"avatar":"https://s3.lexivip.com/avatar-9243461.png","event":4,"quantity":5,"time":2,"username":"超人先生"},{"avatar":"https://s3.lexivip.com/avatar-733789.png","event":4,"quantity":12,"time":1,"username":"何小搭"},{"avatar":"https://s3.lexivip.com/avatar-8793649.png","event":4,"quantity":8,"time":1,"username":"冷洁"},{"avatar":"https://s3.lexivip.com/20181012/5339FqQ0jBomUmLGwqhdRLDxJqHtenTz.png","event":1,"time":7,"time_info":"天前","username":"Fynn"},{"avatar":"https://s3.lexivip.com/avatar-4081598.png","event":1,"time":7,"time_info":"天前","username":"蘑菇蘑菇"},{"avatar":"https://s3.lexivip.com/20181013/0458Fnhof6n6gLY0gRfGOr2wT38oOAbn.jpg","event":1,"time":7,"time_info":"天前","username":"无殇无殇无殇殇无殇"}]}
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
             * avatar : https://s3.lexivip.com/avatar-1542206.png
             * event : 1
             * time : 3
             * time_info : 小时前
             * username : null
             * quantity : 1
             */

            public String avatar;
            public int event;
            public int time;
            public String time_info;
            public Object username;
            public int quantity;
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
