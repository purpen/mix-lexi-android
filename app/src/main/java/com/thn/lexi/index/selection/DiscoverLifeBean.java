package com.thn.lexi.index.selection;

import java.util.List;

public class DiscoverLifeBean {

    /**
     * data : {"count":8,"next":false,"prev":false,"shop_windows":[{"comment_count":"评论数","description":"橱窗详情","is_follow":"是否关注过该橱窗","keywords":["橱窗关键词一","橱窗关键词二"],"like_count":"橱窗喜欢数","products":[{"category_id":18,"commission_price":846.89,"commission_rate":24.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180710/fbba50f0da91a7a.jpg","cover_id":10,"custom_details":"可以刻名字,生辰八字","delivery_country":"","delivery_country_id":null,"features":"获得更多的成长值，加速商铺成长获取更多特权：发布三星级及以上的产品可以获得与星级数量等额的成长值，每日上限为20点；","have_distributed":true,"id_code":"","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":false,"is_sold_out":false,"like_count":1,"made_cycle":5,"material_id":4,"material_name":"毛线","max_price":5456.7,"max_sale_price":0,"min_price":3234.5,"min_sale_price":2245,"modes":["3*1 白色","3*2 绿色","3*3 紫色"],"name":"商品名","published_at":1531213666,"real_price":5456.7,"real_sale_price":0,"rid":"8479032186","second_category_id":17,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"店铺编号","style_id":"商品风格编号","style_name":"商品风格名","top_category_id":1,"total_stock":45}],"rid":"橱窗编号","title":"橱窗标题"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 8
         * next : false
         * prev : false
         * shop_windows : [{"comment_count":"评论数","description":"橱窗详情","is_follow":"是否关注过该橱窗","keywords":["橱窗关键词一","橱窗关键词二"],"like_count":"橱窗喜欢数","products":[{"category_id":18,"commission_price":846.89,"commission_rate":24.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180710/fbba50f0da91a7a.jpg","cover_id":10,"custom_details":"可以刻名字,生辰八字","delivery_country":"","delivery_country_id":null,"features":"获得更多的成长值，加速商铺成长获取更多特权：发布三星级及以上的产品可以获得与星级数量等额的成长值，每日上限为20点；","have_distributed":true,"id_code":"","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":false,"is_sold_out":false,"like_count":1,"made_cycle":5,"material_id":4,"material_name":"毛线","max_price":5456.7,"max_sale_price":0,"min_price":3234.5,"min_sale_price":2245,"modes":["3*1 白色","3*2 绿色","3*3 紫色"],"name":"商品名","published_at":1531213666,"real_price":5456.7,"real_sale_price":0,"rid":"8479032186","second_category_id":17,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"店铺编号","style_id":"商品风格编号","style_name":"商品风格名","top_category_id":1,"total_stock":45}],"rid":"橱窗编号","title":"橱窗标题"}]
         */

        public int count;
        public List<ShopWindowsBean> shop_windows;

        public static class ShopWindowsBean {
            /**
             * comment_count : 评论数
             * description : 橱窗详情
             * is_follow : 是否关注过该橱窗
             * keywords : ["橱窗关键词一","橱窗关键词二"]
             * like_count : 橱窗喜欢数
             * products : [{"category_id":18,"commission_price":846.89,"commission_rate":24.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180710/fbba50f0da91a7a.jpg","cover_id":10,"custom_details":"可以刻名字,生辰八字","delivery_country":"","delivery_country_id":null,"features":"获得更多的成长值，加速商铺成长获取更多特权：发布三星级及以上的产品可以获得与星级数量等额的成长值，每日上限为20点；","have_distributed":true,"id_code":"","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":false,"is_sold_out":false,"like_count":1,"made_cycle":5,"material_id":4,"material_name":"毛线","max_price":5456.7,"max_sale_price":0,"min_price":3234.5,"min_sale_price":2245,"modes":["3*1 白色","3*2 绿色","3*3 紫色"],"name":"商品名","published_at":1531213666,"real_price":5456.7,"real_sale_price":0,"rid":"8479032186","second_category_id":17,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"店铺编号","style_id":"商品风格编号","style_name":"商品风格名","top_category_id":1,"total_stock":45}]
             * rid : 橱窗编号
             * title : 橱窗标题
             */
            public String avatar;
            public String comment_count;
            public String description;
            public String is_follow;
            public String like_count;
            public String rid;
            public String title;
            public List<String> keywords;
            public List<ProductsBean> products;

            public static class ProductsBean {
                /**
                 * category_id : 18
                 * commission_price : 846.89
                 * commission_rate : 24.5
                 * cover : http://127.0.0.1:9000/_uploads/photos/180710/fbba50f0da91a7a.jpg
                 * cover_id : 10
                 * custom_details : 可以刻名字,生辰八字
                 * delivery_country :
                 * delivery_country_id : null
                 * features : 获得更多的成长值，加速商铺成长获取更多特权：发布三星级及以上的产品可以获得与星级数量等额的成长值，每日上限为20点；
                 * have_distributed : true
                 * id_code :
                 * is_custom_made : true
                 * is_custom_service : true
                 * is_distributed : true
                 * is_free_postage : false
                 * is_made_holiday : false
                 * is_proprietary : false
                 * is_sold_out : false
                 * like_count : 1
                 * made_cycle : 5
                 * material_id : 4
                 * material_name : 毛线
                 * max_price : 5456.7
                 * max_sale_price : 0
                 * min_price : 3234.5
                 * min_sale_price : 2245
                 * modes : ["3*1 白色","3*2 绿色","3*3 紫色"]
                 * name : 商品名
                 * published_at : 1531213666
                 * real_price : 5456.7
                 * real_sale_price : 0
                 * rid : 8479032186
                 * second_category_id : 17
                 * status : 1
                 * sticked : false
                 * store_name : 店铺名
                 * store_rid : 店铺编号
                 * style_id : 商品风格编号
                 * style_name : 商品风格名
                 * top_category_id : 1
                 * total_stock : 45
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
                public int max_sale_price;
                public double min_price;
                public int min_sale_price;
                public String name;
                public int published_at;
                public double real_price;
                public int real_sale_price;
                public String rid;
                public int second_category_id;
                public int status;
                public boolean sticked;
                public String store_name;
                public String store_rid;
                public String style_id;
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
