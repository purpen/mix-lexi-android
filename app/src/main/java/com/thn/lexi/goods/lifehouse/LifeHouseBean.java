package com.thn.lexi.goods.lifehouse;

public class LifeHouseBean {

    /**
     * 1  // 生活馆阶段: 1、实习馆主  2、达人馆主
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
        public String logo;
        public String phases_description;
        public String name;
        public int phases;
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
