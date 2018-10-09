package com.thn.lexi.search;

import java.util.List;

public class HotSearchBean {

    /**
     * data : {"count":1,"next":true,"prev":false,"search_items":[{"query_word":"电源","search_at":1520149158,"search_times":1,"total_count":0},{"query_word":"电脑","search_at":1534245501,"search_times":1,"total_count":12}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 1
         * next : true
         * prev : false
         * search_items : [{"query_word":"电源","search_at":1520149158,"search_times":1,"total_count":0},{"query_word":"电脑","search_at":1534245501,"search_times":1,"total_count":12}]
         */

        public int count;
        public List<SearchItemsBean> search_items;

        public static class SearchItemsBean {
            /**
             * query_word : 电源
             * search_at : 1520149158
             * search_times : 1
             * total_count : 0
             */

            public String query_word;
            public int search_at;
            public int search_times;
            public int total_count;
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
