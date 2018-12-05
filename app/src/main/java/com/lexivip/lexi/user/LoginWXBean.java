package com.lexivip.lexi.user;

public class LoginWXBean {
    /**
     * data : {"avatar":"https://s3.lexivip.com/wx_avatar/oUoqn1I1Yz0Qm5y5-njx2t7lvSq4","created_at":1543904312,"expiration":2592000,"is_bind":true,"mobile":"13611086684","openid":"oUoqn1I1Yz0Qm5y5-njx2t7lvSq4","token":"eyJhbGciOiJIUzI1NiIsImlhdCI6MTU0MzkwNDMxMiwiZXhwIjoxNTQ2NDk2MzEyfQ.eyJpZCI6MTYzNjB9.S4NwHW-O5z0xDA3GkuzkDEwYhrxbIuaWxbIalKfBLAw","uid":"19875603241","username":"13611086684"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * avatar : https://s3.lexivip.com/wx_avatar/oUoqn1I1Yz0Qm5y5-njx2t7lvSq4
         * created_at : 1543904312
         * expiration : 2592000
         * is_bind : true
         * mobile : 13611086684
         * openid : oUoqn1I1Yz0Qm5y5-njx2t7lvSq4
         * token : eyJhbGciOiJIUzI1NiIsImlhdCI6MTU0MzkwNDMxMiwiZXhwIjoxNTQ2NDk2MzEyfQ.eyJpZCI6MTYzNjB9.S4NwHW-O5z0xDA3GkuzkDEwYhrxbIuaWxbIalKfBLAw
         * uid : 19875603241
         * username : 13611086684
         */

        public String avatar;
        public int created_at;
        public int expiration;
        public boolean is_bind;
        public String mobile;
        public String openid;
        public String token;
        public String uid;
        public String username;
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
