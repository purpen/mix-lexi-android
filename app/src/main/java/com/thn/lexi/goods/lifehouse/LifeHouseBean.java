package com.thn.lexi.goods.lifehouse;

public class LifeHouseBean {

    /**
     * data : {"description":"大雨还在下,你的心里怕不怕","name":"雨一直下"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * description : 大雨还在下,你的心里怕不怕
         * name : 雨一直下
         */

        public String description;
        public String name;
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
