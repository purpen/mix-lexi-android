package com.thn.lexi.index.detail;

import java.util.List;

public class SimilarGoodsBean {

    /**
     * data : {"count":4,"next":false,"prev":false,"products":[{"category_id":0,"cover":"https://kg.erp.taihuoniao.com/20180402/FrdCY42Eq-cYROi29D1CkhRNZKfh.png","cover_id":511,"custom_details":null,"features":"","id_code":null,"is_custom_made":null,"is_custom_service":null,"is_distributed":null,"is_free_postage":null,"is_made_holiday":null,"is_sold_out":false,"like_count":null,"made_cycle":null,"material_id":null,"material_name":"","max_price":999.99,"max_sale_price":0,"min_price":999.99,"min_sale_price":0,"modes":["1M 白色","2M 红色","1M 绿色"],"name":"万能椅","published_at":0,"rid":"8845617302","second_category_id":0,"status":1,"sticked":true,"top_category_id":0,"total_stock":99},{"category_id":0,"cover":"https://kg.erp.taihuoniao.com/20180226/Fnoungkq94mFVvLYGbN2sod6LjIv.jpg","cover_id":29,"custom_details":null,"features":"","id_code":null,"is_custom_made":null,"is_custom_service":null,"is_distributed":null,"is_free_postage":null,"is_made_holiday":null,"is_sold_out":false,"like_count":null,"made_cycle":null,"material_id":null,"material_name":"","max_price":98,"max_sale_price":88,"min_price":98,"min_sale_price":88,"modes":["黄色","蓝色","白色"],"name":"传奇系列","published_at":0,"rid":"118260327535","second_category_id":0,"status":1,"sticked":true,"top_category_id":0,"total_stock":315},{"category_id":0,"cover":"https://kg.erp.taihuoniao.com/20180226/Fj60jL77HmZQ5Y8mQqXhevARGzZu.jpg","cover_id":59,"custom_details":null,"features":null,"id_code":null,"is_custom_made":null,"is_custom_service":null,"is_distributed":null,"is_free_postage":null,"is_made_holiday":null,"is_sold_out":false,"like_count":null,"made_cycle":null,"material_id":null,"material_name":"","max_price":null,"max_sale_price":49,"min_price":null,"min_sale_price":49,"modes":["棕色","黄色"],"name":"Recci充电线","published_at":0,"rid":"118260368435","second_category_id":0,"status":1,"sticked":true,"top_category_id":0,"total_stock":null},{"category_id":0,"cover":"https://kg.erp.taihuoniao.com/20180226/FvTozbhDCG_VlEcbKcwTZHMGNTbr.jpg","cover_id":76,"custom_details":null,"features":null,"id_code":null,"is_custom_made":null,"is_custom_service":null,"is_distributed":null,"is_free_postage":null,"is_made_holiday":null,"is_sold_out":false,"like_count":null,"made_cycle":null,"material_id":null,"material_name":"","max_price":null,"max_sale_price":89,"min_price":null,"min_sale_price":89,"modes":["红色","银色"],"name":"悦音系列","published_at":0,"rid":"118260197871","second_category_id":0,"status":1,"sticked":true,"top_category_id":0,"total_stock":null}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 4
         * next : false
         * prev : false
         * products : [{"category_id":0,"cover":"https://kg.erp.taihuoniao.com/20180402/FrdCY42Eq-cYROi29D1CkhRNZKfh.png","cover_id":511,"custom_details":null,"features":"","id_code":null,"is_custom_made":null,"is_custom_service":null,"is_distributed":null,"is_free_postage":null,"is_made_holiday":null,"is_sold_out":false,"like_count":null,"made_cycle":null,"material_id":null,"material_name":"","max_price":999.99,"max_sale_price":0,"min_price":999.99,"min_sale_price":0,"modes":["1M 白色","2M 红色","1M 绿色"],"name":"万能椅","published_at":0,"rid":"8845617302","second_category_id":0,"status":1,"sticked":true,"top_category_id":0,"total_stock":99},{"category_id":0,"cover":"https://kg.erp.taihuoniao.com/20180226/Fnoungkq94mFVvLYGbN2sod6LjIv.jpg","cover_id":29,"custom_details":null,"features":"","id_code":null,"is_custom_made":null,"is_custom_service":null,"is_distributed":null,"is_free_postage":null,"is_made_holiday":null,"is_sold_out":false,"like_count":null,"made_cycle":null,"material_id":null,"material_name":"","max_price":98,"max_sale_price":88,"min_price":98,"min_sale_price":88,"modes":["黄色","蓝色","白色"],"name":"传奇系列","published_at":0,"rid":"118260327535","second_category_id":0,"status":1,"sticked":true,"top_category_id":0,"total_stock":315},{"category_id":0,"cover":"https://kg.erp.taihuoniao.com/20180226/Fj60jL77HmZQ5Y8mQqXhevARGzZu.jpg","cover_id":59,"custom_details":null,"features":null,"id_code":null,"is_custom_made":null,"is_custom_service":null,"is_distributed":null,"is_free_postage":null,"is_made_holiday":null,"is_sold_out":false,"like_count":null,"made_cycle":null,"material_id":null,"material_name":"","max_price":null,"max_sale_price":49,"min_price":null,"min_sale_price":49,"modes":["棕色","黄色"],"name":"Recci充电线","published_at":0,"rid":"118260368435","second_category_id":0,"status":1,"sticked":true,"top_category_id":0,"total_stock":null},{"category_id":0,"cover":"https://kg.erp.taihuoniao.com/20180226/FvTozbhDCG_VlEcbKcwTZHMGNTbr.jpg","cover_id":76,"custom_details":null,"features":null,"id_code":null,"is_custom_made":null,"is_custom_service":null,"is_distributed":null,"is_free_postage":null,"is_made_holiday":null,"is_sold_out":false,"like_count":null,"made_cycle":null,"material_id":null,"material_name":"","max_price":null,"max_sale_price":89,"min_price":null,"min_sale_price":89,"modes":["红色","银色"],"name":"悦音系列","published_at":0,"rid":"118260197871","second_category_id":0,"status":1,"sticked":true,"top_category_id":0,"total_stock":null}]
         */

        public int count;
        public boolean next;
        public boolean prev;
        public List<ProductsBean> products;

        public static class ProductsBean {
            /**
             * category_id : 0
             * cover : https://kg.erp.taihuoniao.com/20180402/FrdCY42Eq-cYROi29D1CkhRNZKfh.png
             * cover_id : 511
             * custom_details : null
             * features :
             * id_code : null
             * is_custom_made : null
             * is_custom_service : null
             * is_distributed : null
             * is_free_postage : null
             * is_made_holiday : null
             * is_sold_out : false
             * like_count : null
             * made_cycle : null
             * material_id : null
             * material_name :
             * max_price : 999.99
             * max_sale_price : 0
             * min_price : 999.99
             * min_sale_price : 0
             * modes : ["1M 白色","2M 红色","1M 绿色"]
             * name : 万能椅
             * published_at : 0
             * rid : 8845617302
             * second_category_id : 0
             * status : 1
             * sticked : true
             * top_category_id : 0
             * total_stock : 99
             */

            public int category_id;
            public String cover;
            public int cover_id;
            public String features;
            public boolean is_sold_out;
            public String material_name;
            public double max_price;
            public int max_sale_price;
            public double min_price;
            public int min_sale_price;
            public String name;
            public int published_at;
            public String rid;
            public int second_category_id;
            public int status;
            public boolean sticked;
            public int top_category_id;
            public int total_stock;
            public List<String> modes;
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
