package com.lexivip.lexi.brandPavilion;

import java.util.List;

public class SelectionBrandPavilionListBean {


    /**
     * data : {"handpick_store":[{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1535116196910.jpg","city":"西区","country":"中国台湾","delivery_city":"西区","delivery_country":"中国台湾","delivery_province":"台中市","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/1534518251289.png","name":"作作 ZUO ZUO","pattern":1,"province":"台中市","rid":"97825963","store_products_counts":25,"tag_line":"以手製車縫商品定位，力求手感不朽，實用樸實 、簡約大方、","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1534766856437.PNG","city":"东莞市","country":"中国","delivery_city":"东莞市","delivery_country":"中国","delivery_province":"广东","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/1534766593093.jpg","name":"Squly史古利","pattern":1,"province":"广东","rid":"92508197","store_products_counts":15,"tag_line":"Squly&friends 史古利和朋友们让生活更萌好！","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1534182242810.jpg","city":"东莞市","country":"中国","delivery_city":"东莞市","delivery_country":"中国","delivery_province":"广东","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/1534181278558.png","name":"闺蜜的故事","pattern":1,"province":"广东","rid":"93285769","store_products_counts":4,"tag_line":"一心制一物，很高兴认识你","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1533706205852.jpg","city":"广州市","country":"中国","delivery_city":"广州市","delivery_country":"中国","delivery_province":"广东","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/1533637781199.jpg","name":"方术","pattern":1,"province":"广东","rid":"98079462","store_products_counts":9,"tag_line":"设计新的结构，尝试各种材质搭配，用设计寻找新的使用可能","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/lexi-b/1508576140424.jpg","city":"板桥区","country":"中国台湾","delivery_city":"板桥区","delivery_country":"中国台湾","delivery_province":"新北市","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/lexi-b/1508773541659.gif","name":"Reve手工轻珠宝","pattern":1,"province":"新北市","rid":"99273816","store_products_counts":150,"tag_line":"『独特的设计，严选的材料，精细的作工』","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/20180908/3031FgS8Xxl3hmmyqOsIhw3bLaLjwxlv.jpg","city":"","country":"中国","delivery_city":"朝阳区","delivery_country":"中国","delivery_province":"北京","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/20180908/4437Fsvu_d_fDECJx9f8XpYHxL1Jov2c.jpg","name":"维风设计","pattern":-1,"province":"","rid":"97106542","store_products_counts":1,"tag_line":"欢迎来到我的设计馆","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1535191743059.jpg","city":"东莞市","country":"中国","delivery_city":"东莞市","delivery_country":"中国","delivery_province":"广东","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/1535191708701.jpg","name":"Creative Amazing","pattern":1,"province":"广东","rid":"95176923","store_products_counts":18,"tag_line":"我有一颗匠心 至于CREATIVE AMAZING,原意","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1535095077690.jpg","city":"和平区","country":"中国","delivery_city":"和平区","delivery_country":"中国","delivery_province":"天津","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/1535094737088.jpg","name":"画意坊","pattern":1,"province":"天津","rid":"96432987","store_products_counts":8,"tag_line":"纯手工艺品，私人定制，原创油画","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1535087481782.jpg","city":"杨浦区","country":"中国","delivery_city":"杨浦区","delivery_country":"中国","delivery_province":"上海","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/1535087575973.jpg","name":"瓷语堂屠娟工作室","pattern":1,"province":"上海","rid":"99804216","store_products_counts":6,"tag_line":"纯手工制作，艺术家签名款。","town":""},{"bgcover":"https://kg.erp.taihuoniao.com/lxServer/1534936302217.jpg","city":"嘉定区","country":"中国","delivery_city":"嘉定区","delivery_country":"中国","delivery_province":"上海","is_closed":false,"logo":"https://kg.erp.taihuoniao.com/lxServer/1534936112436.jpg","name":"HYGGERIUM","pattern":1,"province":"上海","rid":"95187302","store_products_counts":10,"tag_line":"生活美学 | THE ART OF LIVING WEL","town":""}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<HandpickStoreBean> handpick_store;

        public static class HandpickStoreBean {
            /**
             * bgcover : https://kg.erp.taihuoniao.com/lxServer/1535116196910.jpg
             * city : 西区
             * country : 中国台湾
             * delivery_city : 西区
             * delivery_country : 中国台湾
             * delivery_province : 台中市
             * is_closed : false
             * logo : https://kg.erp.taihuoniao.com/lxServer/1534518251289.png
             * name : 作作 ZUO ZUO
             * pattern : 1
             * province : 台中市
             * rid : 97825963
             * store_products_counts : 25
             * tag_line : 以手製車縫商品定位，力求手感不朽，實用樸實 、簡約大方、
             * town :
             */

            public String bgcover;
            public String city;
            public String country;
            public String delivery_city;
            public String delivery_country;
            public String delivery_province;
            public boolean is_closed;
            public String logo;
            public String name;
            public int pattern;
            public String province;
            public String rid;
            public int store_products_counts;
            public String tag_line;
            public String town;
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
