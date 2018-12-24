package com.lexivip.lexi.index.detail;

import com.lexivip.lexi.beans.CouponBean;

import java.util.List;

public class OfficialCouponBean {

    /**
     * data : {"count":2,"official_coupons":[{"amount":5,"code":"OUFCNPWIEUH","count":12999,"created_at":1532763535,"end_date":1533398400,"min_amount":200,"pickup_count":0,"start_date":1532707200,"type_text":"满200.00减5.00元","use_count":0}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 2
         * official_coupons : [{"amount":5,"code":"OUFCNPWIEUH","count":12999,"created_at":1532763535,"end_date":1533398400,"min_amount":200,"pickup_count":0,"start_date":1532707200,"type_text":"满200.00减5.00元","use_count":0}]
         */

        public int count;
        public List<CouponBean> official_coupons;

        public static class OfficialCouponsBean {
            /**
             * amount : 5
             * code : OUFCNPWIEUH
             * count : 12999
             * created_at : 1532763535
             * end_date : 1533398400
             * min_amount : 200
             * pickup_count : 0
             * start_date : 1532707200
             * type_text : 满200.00减5.00元
             * use_count : 0
             */

            public int amount;
            public String code;
            public int count;
            public int created_at;
            public int end_date;
            public int min_amount;
            public int pickup_count;
            public int start_date;
            public String type_text;
            public int use_count;
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
