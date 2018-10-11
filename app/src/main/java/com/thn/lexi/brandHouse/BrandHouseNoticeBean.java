package com.thn.lexi.brandHouse;

public class BrandHouseNoticeBean {
    /**
     * data : {"announcement":"速度","begin_date":12345310354,"delivery_date":1543215345,"end_date":15345342245,"is_closed":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * announcement : 速度
         * begin_date : 12345310354
         * delivery_date : 1543215345
         * end_date : 15345342245
         * is_closed : false
         */

        public String announcement;
        public long begin_date;
        public int delivery_date;
        public long end_date;
        public boolean is_closed;
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
