package com.lexivip.lexi.lifeShop;

import java.util.List;

public class RewardBean {
    /**
     * data : {"count":1,"next":false,"prev":false,"rewards":[{"amount":11,"created_at":1544500692,"status":1,"title":"获得队友首单销售50%奖励","user_logo":"https://static.moebeast.com/image/static/default_user.png","user_name":"测试","user_sn":"18913742065"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 1
         * next : false
         * prev : false
         * rewards : [{"amount":11,"created_at":1544500692,"status":1,"title":"获得队友首单销售50%奖励","user_logo":"https://static.moebeast.com/image/static/default_user.png","user_name":"测试","user_sn":"18913742065"}]
         */

        public int count;
        public boolean next;
        public boolean prev;
        public List<RewardsBean> rewards;

        public static class RewardsBean {
            /**
             * amount : 11
             * created_at : 1544500692
             * status : 1
             * title : 获得队友首单销售50%奖励
             * user_logo : https://static.moebeast.com/image/static/default_user.png
             * user_name : 测试
             * user_sn : 18913742065
             */

            public int amount;
            public int created_at;
            public int status;
            public String title;
            public String user_logo;
            public String user_name;
            public String user_sn;
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
