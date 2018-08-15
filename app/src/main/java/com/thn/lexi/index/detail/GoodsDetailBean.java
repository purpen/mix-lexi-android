package com.thn.lexi.index.detail;

import java.util.List;

public class GoodsDetailBean {

    /**
     * data : {"content":"产品内容详情","images":[{"created_at":1527587113,"filename":"m.jpg","filepath":"180529/e5a6b51ad55208d.jpg","id":5,"type":1,"view_url":"http://127.0.0.1:9000/_uploads/photos/180529/e5a6b51ad55208d.jpg"},{"created_at":1527587113,"filename":"l.jpg","filepath":"180529/4158809a4303eab.jpg","id":6,"type":1,"view_url":"http://127.0.0.1:9000/_uploads/photos/180529/4158809a4303eab.jpg"}],"keywords":"苹果,明星,博客,象棋","product_return_policy":"除定制化、一次性使用商品，特殊订单项目外，如果由于个人原因您对购买的礼品不满意，在乐喜您可以在收到订单后隔日起算享受7天内退货或更换礼品服务。礼品寄出和退回运费将由消费者自行负担，请保持礼品及包裝完整寄回，经过创作人确认无误后，即可退换货。"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * content : 产品内容详情
         * images : [{"created_at":1527587113,"filename":"m.jpg","filepath":"180529/e5a6b51ad55208d.jpg","id":5,"type":1,"view_url":"http://127.0.0.1:9000/_uploads/photos/180529/e5a6b51ad55208d.jpg"},{"created_at":1527587113,"filename":"l.jpg","filepath":"180529/4158809a4303eab.jpg","id":6,"type":1,"view_url":"http://127.0.0.1:9000/_uploads/photos/180529/4158809a4303eab.jpg"}]
         * keywords : 苹果,明星,博客,象棋
         * product_return_policy : 除定制化、一次性使用商品，特殊订单项目外，如果由于个人原因您对购买的礼品不满意，在乐喜您可以在收到订单后隔日起算享受7天内退货或更换礼品服务。礼品寄出和退回运费将由消费者自行负担，请保持礼品及包裝完整寄回，经过创作人确认无误后，即可退换货。
         */

        public String content;
        public String keywords;
        public String product_return_policy;
        public List<ImagesBean> images;

        public static class ImagesBean {
            /**
             * created_at : 1527587113
             * filename : m.jpg
             * filepath : 180529/e5a6b51ad55208d.jpg
             * id : 5
             * type : 1
             * view_url : http://127.0.0.1:9000/_uploads/photos/180529/e5a6b51ad55208d.jpg
             */

            public int created_at;
            public String filename;
            public String filepath;
            public int id;
            public int type;
            public String view_url;
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
