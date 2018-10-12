package com.thn.lexi.brandHouse;

import com.thn.lexi.beans.ProductBean;

import java.util.List;

public class BrandHouseGoodsBean {
    /**
     * data : {"count":54,"next":true,"prev":false,"products":[{"category_id":200,"commission_price":30,"commission_rate":6,"cover":"http://xxx/_uploads/photos/171010/fe7380064e18135.jpg","cover_id":32,"custom_details":"定制详情","delivery_country":"北京","delivery_country_id":10,"features":"这是商品推荐语","have_distributed":true,"id_code":"6915545123548","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_like":true,"is_made_holiday":true,"is_sold_out":false,"is_proprietary":false,"is_wish":false,"like_count":333,"made_cycle":5,"max_price":1888,"max_sale_price":1699,"min_price":1388,"min_sale_price":1299,"modes":["大 白色","小 黑色"],"name":"速腾套装","published_at":1546121488,"real_price":200,"real_sale_price":0,"rid":"117210372661","second_category_id":50,"status":1,"stick_text":"这里填写分销推荐语","sticked":null,"store_logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","store_name":"太火鸟店铺","store_rid":"6516561564","style_id":2,"style_name":"简约","top_category_id":1,"total_stock":35}]}
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
         * products : [{"category_id":200,"commission_price":30,"commission_rate":6,"cover":"http://xxx/_uploads/photos/171010/fe7380064e18135.jpg","cover_id":32,"custom_details":"定制详情","delivery_country":"北京","delivery_country_id":10,"features":"这是商品推荐语","have_distributed":true,"id_code":"6915545123548","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_like":true,"is_made_holiday":true,"is_sold_out":false,"is_proprietary":false,"is_wish":false,"like_count":333,"made_cycle":5,"max_price":1888,"max_sale_price":1699,"min_price":1388,"min_sale_price":1299,"modes":["大 白色","小 黑色"],"name":"速腾套装","published_at":1546121488,"real_price":200,"real_sale_price":0,"rid":"117210372661","second_category_id":50,"status":1,"stick_text":"这里填写分销推荐语","sticked":null,"store_logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","store_name":"太火鸟店铺","store_rid":"6516561564","style_id":2,"style_name":"简约","top_category_id":1,"total_stock":35}]
         */

        public int count;
        public boolean next;
        public boolean prev;
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
