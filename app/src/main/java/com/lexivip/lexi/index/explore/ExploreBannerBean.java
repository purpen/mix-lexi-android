package com.lexivip.lexi.index.explore;

import com.lexivip.lexi.index.bean.BannerImageBean;

import java.util.List;

public class ExploreBannerBean {

    /**
     * data : {"banner_images":[{"description":"广告图描述","image":"http://127.0.0.1:9000/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","link":"链接地址","rid":4,"sort_order":"排序","status":true,"title":"标题","type":"0=全部, 1=链接地址, 2=商品, 3=分类, 4=品牌, 5=专题"},{"description":"广告图描述","image":"http://127.0.0.1:9000/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","link":"链接地址","rid":3,"sort_order":1,"status":true,"title":"标题","type":2}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<BannerImageBean> banner_images;


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
