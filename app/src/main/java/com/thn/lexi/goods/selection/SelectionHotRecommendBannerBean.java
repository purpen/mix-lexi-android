package com.thn.lexi.goods.selection;

import java.util.List;

public class SelectionHotRecommendBannerBean {

    /**
     * data : {"banner_images":[]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<String> banner_images;
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
