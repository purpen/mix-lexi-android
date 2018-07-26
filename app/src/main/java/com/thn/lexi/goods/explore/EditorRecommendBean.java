package com.thn.lexi.goods.explore;

import java.util.List;

public class EditorRecommendBean {

    /**
     * data : {"count":6,"products":[{"category_id":"分类id","commission_price":"分销佣金","commission_rate":"佣金比率","cover":"封面图片链接地址","cover_id":"封面图id","custom_details":"定制详情","delivery_country":"发货地名","delivery_country_id":"发货地编号","features":"商品推荐语","have_distributed":"店铺是否分销过该商品","id_code":"商品简码","is_custom_made":"是否接单定制","is_custom_service":"是否支持定制化服务","is_distributed":"是否分销","is_free_postage":"是否包邮","is_like":"用户是否添加喜欢","is_made_holiday":"制作周期是否包含节假日","is_sold_out":"是否已售罄","is_proprietary":"是否自营商品","like_count":"商品被喜欢数","is_wish":"用户是否加入心愿清单","made_cycle":"制作周期,天数","max_price":"最大售价","max_sale_price":"最大促销价","min_price":"最小售价","min_sale_price":"最小促销价","modes":["商品型号1","商品型号2"],"name":"商品名","published_at":"商品发布时间","real_price":"佣金对应的商品原价","real_sale_price":"佣金对应的商品折扣价","rid":"商品编号","second_category_id":"二级分类id","status":"商品状态: 0=仓库中, 1=出售中, 2=下架中","sticked":"是否推荐","store_name":"店铺名","store_rid":"店铺编号","top_category_id":"一级分类id","total_stock":"库存数"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 6
         * products : [{"category_id":"分类id","commission_price":"分销佣金","commission_rate":"佣金比率","cover":"封面图片链接地址","cover_id":"封面图id","custom_details":"定制详情","delivery_country":"发货地名","delivery_country_id":"发货地编号","features":"商品推荐语","have_distributed":"店铺是否分销过该商品","id_code":"商品简码","is_custom_made":"是否接单定制","is_custom_service":"是否支持定制化服务","is_distributed":"是否分销","is_free_postage":"是否包邮","is_like":"用户是否添加喜欢","is_made_holiday":"制作周期是否包含节假日","is_sold_out":"是否已售罄","is_proprietary":"是否自营商品","like_count":"商品被喜欢数","is_wish":"用户是否加入心愿清单","made_cycle":"制作周期,天数","max_price":"最大售价","max_sale_price":"最大促销价","min_price":"最小售价","min_sale_price":"最小促销价","modes":["商品型号1","商品型号2"],"name":"商品名","published_at":"商品发布时间","real_price":"佣金对应的商品原价","real_sale_price":"佣金对应的商品折扣价","rid":"商品编号","second_category_id":"二级分类id","status":"商品状态: 0=仓库中, 1=出售中, 2=下架中","sticked":"是否推荐","store_name":"店铺名","store_rid":"店铺编号","top_category_id":"一级分类id","total_stock":"库存数"}]
         */

        public int count;
        public List<ProductsBean> products;

        public static class ProductsBean {
            /**
             * category_id : 分类id
             * commission_price : 分销佣金
             * commission_rate : 佣金比率
             * cover : 封面图片链接地址
             * cover_id : 封面图id
             * custom_details : 定制详情
             * delivery_country : 发货地名
             * delivery_country_id : 发货地编号
             * features : 商品推荐语
             * have_distributed : 店铺是否分销过该商品
             * id_code : 商品简码
             * is_custom_made : 是否接单定制
             * is_custom_service : 是否支持定制化服务
             * is_distributed : 是否分销
             * is_free_postage : 是否包邮
             * is_like : 用户是否添加喜欢
             * is_made_holiday : 制作周期是否包含节假日
             * is_sold_out : 是否已售罄
             * is_proprietary : 是否自营商品
             * like_count : 商品被喜欢数
             * is_wish : 用户是否加入心愿清单
             * made_cycle : 制作周期,天数
             * max_price : 最大售价
             * max_sale_price : 最大促销价
             * min_price : 最小售价
             * min_sale_price : 最小促销价
             * modes : ["商品型号1","商品型号2"]
             * name : 商品名
             * published_at : 商品发布时间
             * real_price : 佣金对应的商品原价
             * real_sale_price : 佣金对应的商品折扣价
             * rid : 商品编号
             * second_category_id : 二级分类id
             * status : 商品状态: 0=仓库中, 1=出售中, 2=下架中
             * sticked : 是否推荐
             * store_name : 店铺名
             * store_rid : 店铺编号
             * top_category_id : 一级分类id
             * total_stock : 库存数
             */

            public String category_id;
            public String commission_price;
            public String commission_rate;
            public String cover;
            public String cover_id;
            public String custom_details;
            public String delivery_country;
            public String delivery_country_id;
            public String features;
            public String have_distributed;
            public String id_code;
            public String is_custom_made;
            public String is_custom_service;
            public String is_distributed;
            public String is_free_postage;
            public String is_like;
            public String is_made_holiday;
            public String is_sold_out;
            public String is_proprietary;
            public String like_count;
            public String is_wish;
            public String made_cycle;
            public String max_price;
            public String max_sale_price;
            public String min_price;
            public String min_sale_price;
            public String name;
            public String published_at;
            public String real_price;
            public String real_sale_price;
            public String rid;
            public String second_category_id;
            public String status;
            public String sticked;
            public String store_name;
            public String store_rid;
            public String top_category_id;
            public String total_stock;
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
