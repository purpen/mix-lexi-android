package com.lexivip.lexi.receiveVoucher;

import java.util.List;

public class VoucherNoticeBean {
    /**
     * data : {"coupon_lines":[{"activity_id":2,"activity_type":2,"amount":2,"coupon_line_id":1,"coupon_type":2,"created_at":1530781909,"event":2,"line_text":"888小时前小黑在分享红包游戏中被赠送2元官方优惠券","quantity":2,"store_name":"第一家","user_info":{"user_logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"15210062187","user_sn":"17160283459"}}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<CouponLinesBean> coupon_lines;

        public static class CouponLinesBean {
            /**
             * activity_id : 2
             * activity_type : 2
             * amount : 2
             * coupon_line_id : 1
             * coupon_type : 2
             * created_at : 1530781909
             * event : 2
             * line_text : 888小时前小黑在分享红包游戏中被赠送2元官方优惠券
             * quantity : 2
             * store_name : 第一家
             * user_info : {"user_logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"15210062187","user_sn":"17160283459"}
             */

            public int activity_id;
            public int activity_type;
            public int amount;
            public int coupon_line_id;
            public int coupon_type;
            public int created_at;
            public int event;
            public String line_text;
            public int quantity;
            public String store_name;
            public UserInfoBean user_info;

            public static class UserInfoBean {
                /**
                 * user_logo : http://kg.erp.taihuoniao.com/static/img/default-logo.png
                 * user_name : 15210062187
                 * user_sn : 17160283459
                 */

                public String user_logo;
                public String user_name;
                public String user_sn;
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
