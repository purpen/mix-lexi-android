package com.lexivip.lexi.index.explore;

import com.lexivip.lexi.index.bean.BannerImageBean;

import java.util.List;

public class ExploreBannerBean {

    /**
     * data : {"banner_images":[{"image":"https://s3.lexivip.com/20181108/2433FsMHYLW7l0dwsfAzBOnB7zj3PdLC.png","link":" 97239150","type":4},{"image":"https://s3.lexivip.com/20181108/2329FtyeCOnnQUpJJ1eyhWa4iPpN5j19.png","link":"92081537","type":2},{"image":"https://s3.lexivip.com/20181108/2156FpMjNndApB5JkO7CTrP-DcDnB_j8.png","link":"8930587614","type":2},{"image":"https://s3.lexivip.com/20181108/2039Fn_0IxucI6PAtnBQ9vWZ0C7tSH16.png","link":"8853072194","type":2}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<BannerImageBean> banner_images;

//        public static class BannerImagesBean {
//            /**
//             * image : https://s3.lexivip.com/20181108/2433FsMHYLW7l0dwsfAzBOnB7zj3PdLC.png
//             * link :  97239150
//             * type : 4
//             */
//
//            public String image;
//            public String link;
//            public int type;
//        }
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
