package com.thn.lexi.index.detail;

public class PosterBean {

    /**
     * data : {"image_url":"https://kg.erp.taihuoniao.com/20180314/Fk1vEAP_tIVwmPRfHRfl8jpn07CZ.png"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * image_url : https://kg.erp.taihuoniao.com/20180314/Fk1vEAP_tIVwmPRfHRfl8jpn07CZ.png
         */

        public String image_url;
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
