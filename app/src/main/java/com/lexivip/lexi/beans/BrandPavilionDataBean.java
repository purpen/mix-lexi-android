package com.lexivip.lexi.beans;

public class BrandPavilionDataBean {

    /**
     * data : {"bgcover":"https://kg.erp.taihuoniao.com/20180716/2634FlH_a8eIHd-bUpHs72zlgnt3I8Rz.jpg","categories":[[220,"科技"],[261,"玩具&娱乐"],[376,"好感衣装"]],"city":"朝阳区","country":"中国","created_at":1531722293,"delivery_city":"朝阳区","delivery_country":"中国","delivery_province":"北京","fans_count":0,"is_followed":false,"life_record_count":2,"logo":"https://kg.erp.taihuoniao.com/20180716/2623FopwvnJY8ORXMTqU42vPisc4V0_e.jpg","name":"798艺术区","product_count":3,"province":"北京","rid":"94952308","tag_line":"您的满意，是对我们服务的最大肯定，我们将用心做的更好！","town":""}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public BrandPavilionBean data;
    public StatusBean status;
    public boolean success;

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
