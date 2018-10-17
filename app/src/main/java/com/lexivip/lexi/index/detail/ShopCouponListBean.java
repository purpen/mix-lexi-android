package com.lexivip.lexi.index.detail;

import com.lexivip.lexi.beans.CouponBean;

import java.util.List;

public class ShopCouponListBean {

    /**
     * data : {"coupons":[{"amount":10,"code":"UHAOSIWFUVZ","count":100,"created_at":1531742069,"days":7,"min_amount":99,"products":[],"reach_amount":0,"status":1,"type":1,"type_text":"全店通用","end_date":1538582400,"start_date":1531670400}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<CouponBean> coupons;

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
