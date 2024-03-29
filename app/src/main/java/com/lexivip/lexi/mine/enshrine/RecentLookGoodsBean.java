package com.lexivip.lexi.mine.enshrine;

import com.lexivip.lexi.beans.ProductBean;

import java.util.List;

public class RecentLookGoodsBean {

    /**
     * data : {"count":54,"next":true,"prev":false,"products":[{"category_id":"分类id","commission_price":"分销佣金","commission_rate":"佣金比率","cover":"封面图片链接地址","cover_id":"封面图id","custom_details":"定制详情","delivery_country":"发货地名","delivery_country_id":"发货地编号","features":"商品推荐语","have_distributed":"店铺是否分销过该商品","id_code":"商品简码","is_custom_made":"是否接单定制","is_custom_service":"是否支持定制化服务","is_distributed":"是否分销","is_free_postage":"是否包邮","is_like":"用户是否添加喜欢","is_made_holiday":"制作周期是否包含节假日","is_sold_out":"是否已售罄","is_proprietary":"是否自营商品","like_count":"商品被喜欢数","is_wish":"用户是否加入心愿清单","made_cycle":"制作周期,天数","max_price":"最大售价","max_sale_price":"最大促销价","min_price":"最小售价","min_sale_price":"最小促销价","modes":["商品型号1","商品型号2"],"name":"商品名","published_at":"商品发布时间","real_price":"佣金对应的商品原价","real_sale_price":"佣金对应的商品折扣价","rid":"商品编号","second_category_id":"二级分类id","status":"商品状态: 0=仓库中, 1=出售中, 2=下架中","stick_text":"分销推荐语","sticked":"是否推荐","store_logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","store_name":"店铺名","store_rid":"店铺编号","style_id":"商品风格编号","style_name":"商品风格名","top_category_id":"一级分类id","total_stock":"库存数"},{"category_id":200,"commission_price":30,"commission_rate":6,"cover":"http://xxx/_uploads/photos/171010/fe7380064e18135.jpg","cover_id":32,"custom_details":"定制详情","delivery_country":"北京","delivery_country_id":10,"features":"这是商品推荐语","have_distributed":true,"id_code":"6915545123548","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_like":true,"is_made_holiday":true,"is_sold_out":false,"is_proprietary":false,"is_wish":false,"like_count":333,"made_cycle":5,"max_price":1888,"max_sale_price":1699,"min_price":1388,"min_sale_price":1299,"modes":["大 白色","小 黑色"],"name":"速腾套装","published_at":1546121488,"real_price":200,"real_sale_price":0,"rid":"117210372661","second_category_id":50,"status":1,"stick_text":"这里填写分销推荐语","sticked":null,"store_logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","store_name":"太火鸟店铺","store_rid":"6516561564","style_id":2,"style_name":"简约","top_category_id":1,"total_stock":35}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 54
         * next : true
         * prev : false
         * products : [{"category_id":"分类id","commission_price":"分销佣金","commission_rate":"佣金比率","cover":"封面图片链接地址","cover_id":"封面图id","custom_details":"定制详情","delivery_country":"发货地名","delivery_country_id":"发货地编号","features":"商品推荐语","have_distributed":"店铺是否分销过该商品","id_code":"商品简码","is_custom_made":"是否接单定制","is_custom_service":"是否支持定制化服务","is_distributed":"是否分销","is_free_postage":"是否包邮","is_like":"用户是否添加喜欢","is_made_holiday":"制作周期是否包含节假日","is_sold_out":"是否已售罄","is_proprietary":"是否自营商品","like_count":"商品被喜欢数","is_wish":"用户是否加入心愿清单","made_cycle":"制作周期,天数","max_price":"最大售价","max_sale_price":"最大促销价","min_price":"最小售价","min_sale_price":"最小促销价","modes":["商品型号1","商品型号2"],"name":"商品名","published_at":"商品发布时间","real_price":"佣金对应的商品原价","real_sale_price":"佣金对应的商品折扣价","rid":"商品编号","second_category_id":"二级分类id","status":"商品状态: 0=仓库中, 1=出售中, 2=下架中","stick_text":"分销推荐语","sticked":"是否推荐","store_logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","store_name":"店铺名","store_rid":"店铺编号","style_id":"商品风格编号","style_name":"商品风格名","top_category_id":"一级分类id","total_stock":"库存数"},{"category_id":200,"commission_price":30,"commission_rate":6,"cover":"http://xxx/_uploads/photos/171010/fe7380064e18135.jpg","cover_id":32,"custom_details":"定制详情","delivery_country":"北京","delivery_country_id":10,"features":"这是商品推荐语","have_distributed":true,"id_code":"6915545123548","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_like":true,"is_made_holiday":true,"is_sold_out":false,"is_proprietary":false,"is_wish":false,"like_count":333,"made_cycle":5,"max_price":1888,"max_sale_price":1699,"min_price":1388,"min_sale_price":1299,"modes":["大 白色","小 黑色"],"name":"速腾套装","published_at":1546121488,"real_price":200,"real_sale_price":0,"rid":"117210372661","second_category_id":50,"status":1,"stick_text":"这里填写分销推荐语","sticked":null,"store_logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","store_name":"太火鸟店铺","store_rid":"6516561564","style_id":2,"style_name":"简约","top_category_id":1,"total_stock":35}]
         */

        public int count;
        public List<ProductBean> products;
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
