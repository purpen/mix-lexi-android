package com.lexivip.lexi.mine.dynamic;

import java.util.List;

public class DynamicBean {

    /**
     * data : {"bg_cover":"https://kg.erp.taihuoniao.com/static/img/default-logo.png","count":0,"followed_status":0,"lines":[],"user_avatar":"https://kg.erp.taihuoniao.com/20180811/1421FtW5tzTu5YcqZh8HFCsC4w9oKq2K.png","username":"Rap"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * bg_cover : https://kg.erp.taihuoniao.com/static/img/default-logo.png
         * count : 0
         * followed_status : 0
         * lines : []
         * user_avatar : https://kg.erp.taihuoniao.com/20180811/1421FtW5tzTu5YcqZh8HFCsC4w9oKq2K.png
         * username : Rap
         */

        public String bg_cover;
        public int count;
        public int followed_status;
        public String user_avatar;
        public String username;
        public List<?> lines;
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
