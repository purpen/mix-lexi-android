package com.lexivip.lexi.index.selection;

import com.lexivip.lexi.beans.LifeWillBean;

import java.util.List;

public class ZCManifestBean {

    /**
     * data : {"count":4,"life_records":[{"cover":"https://s3.lexivip.com/20181011/4421FjMbRwGmbdoUEQsC9eJbkaATrZUC.jpg","description":"\u201c天然质朴、混然天成的东西，从来都是世界最美、最纯粹，如树木\u201d树木透过吸收阳光和雨水，茁壮成长。它用自己的生","rid":45,"title":"跨越自然的界限，把树木披在身上","type":2,"uid":"14765209381","user_avator":"https://s3.lexivip.com/20181007/2706FoKWFPQyR8w-2k6EkeA6s3_vo4T_.jpg","user_name":"一位设计家"},{"cover":"https://s3.lexivip.com/20181011/5230FssKDFOMBB4lQjtFuJBbtmwQ66Ib.png","description":"在这个快节奏的社会，每个人都在不同的身份中转换，然而穿搭难免有时候会不太合时宜！一款适用于众多场合的百搭包包","rid":41,"title":"一间关于生活手作美学的小馆","type":2,"uid":"12138705964","user_avator":"https://s3.lexivip.com/20181007/1453FlbKU3Z2OZXGyQxwXnZsAuO4CFnt.jpg","user_name":"陈紫涵"},{"cover":"https://s3.lexivip.com/20181011/1728Fjo4WY2guJyy5JSBto6nTBZc-vKt.jpg","description":"我不太喜欢大牌的包，穿搭起来免不了胭脂气，虚高的价格，占着最优越的资源，却不愿意去发挥材料的极致。还有些没什","rid":44,"title":"一个原创小众品牌 满足了我对复古皮包的所有幻想","type":2,"uid":"10296358471","user_avator":"https://s3.lexivip.com/20181007/2850FtSsQ-OhpZF4aV3NE9NrrvRwVEY2.jpg","user_name":"十二卓哥"},{"cover":"https://s3.lexivip.com/20181011/0240FqEPBTiYdPwPwKVvYaq6ayWODVNu.jpg","description":"虽然已经长大成年，但还是经常想起儿时的一些好玩有趣的玩意。那时候的我们还是天真的孩子，一个简易的玩具都能让我","rid":43,"title":"还记得那时候迎风奔跑的我们吗？","type":2,"uid":"12091468357","user_avator":"https://s3.lexivip.com/20181007/2800FtcAvueB-xARDvaiG7bL23gyN1uH.jpg","user_name":"太阳神"}],"next":false,"prev":false,"title":"种草清单"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 4
         * life_records : [{"cover":"https://s3.lexivip.com/20181011/4421FjMbRwGmbdoUEQsC9eJbkaATrZUC.jpg","description":"\u201c天然质朴、混然天成的东西，从来都是世界最美、最纯粹，如树木\u201d树木透过吸收阳光和雨水，茁壮成长。它用自己的生","rid":45,"title":"跨越自然的界限，把树木披在身上","type":2,"uid":"14765209381","user_avator":"https://s3.lexivip.com/20181007/2706FoKWFPQyR8w-2k6EkeA6s3_vo4T_.jpg","user_name":"一位设计家"},{"cover":"https://s3.lexivip.com/20181011/5230FssKDFOMBB4lQjtFuJBbtmwQ66Ib.png","description":"在这个快节奏的社会，每个人都在不同的身份中转换，然而穿搭难免有时候会不太合时宜！一款适用于众多场合的百搭包包","rid":41,"title":"一间关于生活手作美学的小馆","type":2,"uid":"12138705964","user_avator":"https://s3.lexivip.com/20181007/1453FlbKU3Z2OZXGyQxwXnZsAuO4CFnt.jpg","user_name":"陈紫涵"},{"cover":"https://s3.lexivip.com/20181011/1728Fjo4WY2guJyy5JSBto6nTBZc-vKt.jpg","description":"我不太喜欢大牌的包，穿搭起来免不了胭脂气，虚高的价格，占着最优越的资源，却不愿意去发挥材料的极致。还有些没什","rid":44,"title":"一个原创小众品牌 满足了我对复古皮包的所有幻想","type":2,"uid":"10296358471","user_avator":"https://s3.lexivip.com/20181007/2850FtSsQ-OhpZF4aV3NE9NrrvRwVEY2.jpg","user_name":"十二卓哥"},{"cover":"https://s3.lexivip.com/20181011/0240FqEPBTiYdPwPwKVvYaq6ayWODVNu.jpg","description":"虽然已经长大成年，但还是经常想起儿时的一些好玩有趣的玩意。那时候的我们还是天真的孩子，一个简易的玩具都能让我","rid":43,"title":"还记得那时候迎风奔跑的我们吗？","type":2,"uid":"12091468357","user_avator":"https://s3.lexivip.com/20181007/2800FtcAvueB-xARDvaiG7bL23gyN1uH.jpg","user_name":"太阳神"}]
         * next : false
         * prev : false
         * title : 种草清单
         */

        public int count;
        public String title;
        public List<LifeWillBean> life_records;

//        public static class LifeRecordsBean {
            /**
             * cover : https://s3.lexivip.com/20181011/4421FjMbRwGmbdoUEQsC9eJbkaATrZUC.jpg
             * description : “天然质朴、混然天成的东西，从来都是世界最美、最纯粹，如树木”树木透过吸收阳光和雨水，茁壮成长。它用自己的生
             * rid : 45
             * title : 跨越自然的界限，把树木披在身上
             * type : 2
             * uid : 14765209381
             * user_avator : https://s3.lexivip.com/20181007/2706FoKWFPQyR8w-2k6EkeA6s3_vo4T_.jpg
             * user_name : 一位设计家
             */

//            public String cover;
//            public String description;
//            public int rid;
//            public String title;
//            public int type;
//            public String uid;
//            public String user_avator;
//            public String user_name;
//        }
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
