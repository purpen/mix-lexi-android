package com.thn.lexi.goods.selection;

import java.util.List;

public class SelectionHotRecommendBannerBean {


    /**
     * data : {"banner_images":[{"description":"榛果贵妃糖拿铁","image":"https://kg.erp.taihuoniao.com/20180715/0840Fsv2ZsVZckDbiL3U5lsHuLwdKtnj.jpg","link":"118180235433","rid":20,"sort_order":1,"status":true,"title":"贝塔咖啡","type":2},{"description":"我的","image":"https://kg.erp.taihuoniao.com/20180716/4335Fgu7TAdtqUayfQMdxsac4Yn4-Fl3.jpg","link":"8086471253","rid":19,"sort_order":1,"status":true,"title":"小灵兽设计店","type":2},{"description":"新型水壶","image":"https://kg.erp.taihuoniao.com/20180716/1331FnI3uN618pg92IPa6azmTQDDGBmn.jpg","link":"8834021976","rid":18,"sort_order":1,"status":true,"title":"喔喔店","type":2}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<BannerImagesBean> banner_images;

        public static class BannerImagesBean {
            /**
             * description : 榛果贵妃糖拿铁
             * image : https://kg.erp.taihuoniao.com/20180715/0840Fsv2ZsVZckDbiL3U5lsHuLwdKtnj.jpg
             * link : 118180235433
             * rid : 20
             * sort_order : 1
             * status : true
             * title : 贝塔咖啡
             * type : 2
             */

            public String description;
            public String image;
            public String link;
            public int rid;
            public int sort_order;
            public boolean status;
            public String title;
            public int type;
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
