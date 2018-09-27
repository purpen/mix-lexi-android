package com.thn.lexi.search;

import java.util.List;

public class SearchHotRecommendPavilionBean {

    /**
     * data : {"hot_recommends":[{"recommend_cover":"设置的封面图","recommend_cover_id":"封面图编号","recommend_id":"热门推荐编号","recommend_title":"设置的标题","target_type":"1=商品, 2=店铺"},{"recommend_cover":"设置的封面图","recommend_cover_id":"封面图编号","recommend_id":"热门推荐编号","recommend_title":"设置的标题","target_type":"1=商品, 2=店铺"},{"recommend_cover":"设置的封面图","recommend_cover_id":"封面图编号","recommend_id":"热门推荐编号","recommend_title":"设置的标题","target_type":"1=商品, 2=店铺"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<HotRecommendsBean> hot_recommends;

        public static class HotRecommendsBean {
            /**
             * recommend_cover : 设置的封面图
             * recommend_cover_id : 封面图编号
             * recommend_id : 热门推荐编号
             * recommend_title : 设置的标题
             * target_type : 1=商品, 2=店铺
             */

            public int coverId;
            public String recommend_cover;
            public String recommend_cover_id;
            public String recommend_id;
            public String recommend_title;
            public String target_type;
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
