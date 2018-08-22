package com.thn.lexi.discoverLifeAesthetics;

import java.util.List;

public class ShowWindowCommentListBean {

    /**
     * data : {"comments":[{"comment_id":1,"content":"你说的真好","created_at":1533086565,"is_praise":true,"pid":0,"praise_count":1,"sub_comment_count":4,"sub_comments":[{"comment_id":14,"content":"订单！！！","created_at":1533557451,"is_praise":false,"pid":1,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"},{"comment_id":13,"content":"订单！！！","created_at":1533095638,"is_praise":false,"pid":1,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"}],"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"},{"comment_id":2,"content":"你说的真好！！！","created_at":1533086656,"is_praise":false,"pid":0,"praise_count":0,"sub_comment_count":4,"sub_comments":[{"comment_id":10,"content":"是打撒个发！！！","created_at":1533095621,"is_praise":false,"pid":2,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"},{"comment_id":9,"content":"是打发！！！","created_at":1533095618,"is_praise":false,"pid":2,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"}],"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"15210062187"}],"count":2,"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * comments : [{"comment_id":1,"content":"你说的真好","created_at":1533086565,"is_praise":true,"pid":0,"praise_count":1,"sub_comment_count":4,"sub_comments":[{"comment_id":14,"content":"订单！！！","created_at":1533557451,"is_praise":false,"pid":1,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"},{"comment_id":13,"content":"订单！！！","created_at":1533095638,"is_praise":false,"pid":1,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"}],"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"},{"comment_id":2,"content":"你说的真好！！！","created_at":1533086656,"is_praise":false,"pid":0,"praise_count":0,"sub_comment_count":4,"sub_comments":[{"comment_id":10,"content":"是打撒个发！！！","created_at":1533095621,"is_praise":false,"pid":2,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"},{"comment_id":9,"content":"是打发！！！","created_at":1533095618,"is_praise":false,"pid":2,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"}],"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"15210062187"}]
         * count : 2
         * next : false
         * prev : false
         */

        public int count;
        public List<CommentsBean> comments;

        public static class CommentsBean {
            /**
             * comment_id : 1
             * content : 你说的真好
             * created_at : 1533086565
             * is_praise : true
             * pid : 0
             * praise_count : 1
             * sub_comment_count : 4
             * sub_comments : [{"comment_id":14,"content":"订单！！！","created_at":1533557451,"is_praise":false,"pid":1,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"},{"comment_id":13,"content":"订单！！！","created_at":1533095638,"is_praise":false,"pid":1,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"商品棒棒棒1(作者)"}]
             * user_avatar : http://kg.erp.taihuoniao.com/static/img/default-logo.png
             * user_name : 商品棒棒棒1(作者)
             */

            public int comment_id;
            public String content;
            public long created_at;
            public boolean is_praise;
            public int pid;
            public int praise_count;
            public int sub_comment_count;
            public String user_avatar;
            public String user_name;
            public List<SubCommentsBean> sub_comments;

            public static class SubCommentsBean {
                /**
                 * comment_id : 14
                 * content : 订单！！！
                 * created_at : 1533557451
                 * is_praise : false
                 * pid : 1
                 * praise_count : 0
                 * user_avatar : http://kg.erp.taihuoniao.com/static/img/default-logo.png
                 * user_name : 商品棒棒棒1(作者)
                 */

                public int comment_id;
                public String content;
                public long created_at;
                public boolean is_praise;
                public int pid;
                public int praise_count;
                public String user_avatar;
                public String user_name;
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
