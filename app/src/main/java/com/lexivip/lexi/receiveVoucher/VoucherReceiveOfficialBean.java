package com.lexivip.lexi.receiveVoucher;

public class VoucherReceiveOfficialBean {
    /**
     * data : {"amount":10,"code":"OUFCNPWIEUH","expired_at":1533312000,"is_expired":false,"is_used":false,"min_amount":100,"start_at":1532707200,"type":2,"user_id":2}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public VoucherOfficialBean.DataBean.OfficialCouponsBean data;
    public StatusBean status;
    public boolean success;

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
