package com.thn.lexi.goods.explore;

import java.util.List;

public class GoodsCollectionBean {

    /**
     * data : {"collections":[{"cover":"http://127.0.0.1:9000/_uploads/photos/180707/61b85f8fe19d59f.jpg","cover_id":7,"id":7,"mask_color":"遮罩颜色","name":"主题标题","products":[{"category_id":628,"commission_price":246.68,"commission_rate":11.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","cover_id":14,"custom_details":"","delivery_country":"","delivery_country_id":null,"features":"价格超值(5) 大小合适(4) 面料舒适(4) 尺码精准(3) 尺码合适(3) 做工精良(2) 简单得体(2) 使命必达 简约大方","have_distributed":false,"id_code":"sd3223","is_custom_made":false,"is_custom_service":false,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":true,"is_sold_out":false,"like_count":0,"made_cycle":0,"material_id":4,"material_name":"毛线","max_price":2234.5,"max_sale_price":2145,"min_price":1234.5,"min_sale_price":1145,"modes":["大 白色23","小 白色22"],"name":"宝利博纳 夏季新款修身短袖t恤男韩版潮流男士翻领polo衫男体恤","published_at":1532155598,"real_price":2234.5,"real_sale_price":2145,"rid":"8269513870","second_category_id":628,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"1234567891","style_id":null,"style_name":"","top_category_id":600,"total_stock":10}],"rids":["8269513870"],"sort_order":1,"sub_name":"主题标题二"}],"count":2,"next":false,"prev":false,"title":"集合"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * collections : [{"cover":"http://127.0.0.1:9000/_uploads/photos/180707/61b85f8fe19d59f.jpg","cover_id":7,"id":7,"mask_color":"遮罩颜色","name":"主题标题","products":[{"category_id":628,"commission_price":246.68,"commission_rate":11.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","cover_id":14,"custom_details":"","delivery_country":"","delivery_country_id":null,"features":"价格超值(5) 大小合适(4) 面料舒适(4) 尺码精准(3) 尺码合适(3) 做工精良(2) 简单得体(2) 使命必达 简约大方","have_distributed":false,"id_code":"sd3223","is_custom_made":false,"is_custom_service":false,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":true,"is_sold_out":false,"like_count":0,"made_cycle":0,"material_id":4,"material_name":"毛线","max_price":2234.5,"max_sale_price":2145,"min_price":1234.5,"min_sale_price":1145,"modes":["大 白色23","小 白色22"],"name":"宝利博纳 夏季新款修身短袖t恤男韩版潮流男士翻领polo衫男体恤","published_at":1532155598,"real_price":2234.5,"real_sale_price":2145,"rid":"8269513870","second_category_id":628,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"1234567891","style_id":null,"style_name":"","top_category_id":600,"total_stock":10}],"rids":["8269513870"],"sort_order":1,"sub_name":"主题标题二"}]
         * count : 2
         * next : false
         * prev : false
         * title : 集合
         */

        public int count;
        public boolean next;
        public boolean prev;
        public String title;
        public List<CollectionsBean> collections;

        public static class CollectionsBean {
            /**
             * cover : http://127.0.0.1:9000/_uploads/photos/180707/61b85f8fe19d59f.jpg
             * cover_id : 7
             * id : 7
             * mask_color : 遮罩颜色
             * name : 主题标题
             * products : [{"category_id":628,"commission_price":246.68,"commission_rate":11.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","cover_id":14,"custom_details":"","delivery_country":"","delivery_country_id":null,"features":"价格超值(5) 大小合适(4) 面料舒适(4) 尺码精准(3) 尺码合适(3) 做工精良(2) 简单得体(2) 使命必达 简约大方","have_distributed":false,"id_code":"sd3223","is_custom_made":false,"is_custom_service":false,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":true,"is_sold_out":false,"like_count":0,"made_cycle":0,"material_id":4,"material_name":"毛线","max_price":2234.5,"max_sale_price":2145,"min_price":1234.5,"min_sale_price":1145,"modes":["大 白色23","小 白色22"],"name":"宝利博纳 夏季新款修身短袖t恤男韩版潮流男士翻领polo衫男体恤","published_at":1532155598,"real_price":2234.5,"real_sale_price":2145,"rid":"8269513870","second_category_id":628,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"1234567891","style_id":null,"style_name":"","top_category_id":600,"total_stock":10}]
             * rids : ["8269513870"]
             * sort_order : 1
             * sub_name : 主题标题二
             */

            public String cover;
            public int cover_id;
            public int id;
            public String mask_color;
            public String name;
            public int sort_order;
            public String sub_name;
            public List<ProductsBean> products;
            public List<String> rids;

            public static class ProductsBean {
                /**
                 * category_id : 628
                 * commission_price : 246.68
                 * commission_rate : 11.5
                 * cover : http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif
                 * cover_id : 14
                 * custom_details :
                 * delivery_country :
                 * delivery_country_id : null
                 * features : 价格超值(5) 大小合适(4) 面料舒适(4) 尺码精准(3) 尺码合适(3) 做工精良(2) 简单得体(2) 使命必达 简约大方
                 * have_distributed : false
                 * id_code : sd3223
                 * is_custom_made : false
                 * is_custom_service : false
                 * is_distributed : true
                 * is_free_postage : false
                 * is_made_holiday : false
                 * is_proprietary : true
                 * is_sold_out : false
                 * like_count : 0
                 * made_cycle : 0
                 * material_id : 4
                 * material_name : 毛线
                 * max_price : 2234.5
                 * max_sale_price : 2145
                 * min_price : 1234.5
                 * min_sale_price : 1145
                 * modes : ["大 白色23","小 白色22"]
                 * name : 宝利博纳 夏季新款修身短袖t恤男韩版潮流男士翻领polo衫男体恤
                 * published_at : 1532155598
                 * real_price : 2234.5
                 * real_sale_price : 2145
                 * rid : 8269513870
                 * second_category_id : 628
                 * status : 1
                 * sticked : false
                 * store_name : 店铺名
                 * store_rid : 1234567891
                 * style_id : null
                 * style_name :
                 * top_category_id : 600
                 * total_stock : 10
                 */

                public int category_id;
                public double commission_price;
                public double commission_rate;
                public String cover;
                public int cover_id;
                public String custom_details;
                public String delivery_country;
                public String features;
                public boolean have_distributed;
                public String id_code;
                public boolean is_custom_made;
                public boolean is_custom_service;
                public boolean is_distributed;
                public boolean is_free_postage;
                public boolean is_made_holiday;
                public boolean is_proprietary;
                public boolean is_sold_out;
                public int like_count;
                public int made_cycle;
                public int material_id;
                public String material_name;
                public double max_price;
                public double max_sale_price;
                public double min_price;
                public double min_sale_price;
                public String name;
                public int published_at;
                public double real_price;
                public double real_sale_price;
                public String rid;
                public int second_category_id;
                public int status;
                public boolean sticked;
                public String store_name;
                public String store_rid;
                public String style_name;
                public int top_category_id;
                public int total_stock;
                public List<String> modes;
            }
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
