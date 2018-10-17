package com.lexivip.lexi.order;

import java.util.List;

public class SelectExpressResponseBean {

    /**
     * data : [{"express_code":"YTO","express_id":4,"express_name":"圆通","freight":350,"max_days":3,"min_days":3},{"express_code":"ZJS","express_id":6,"express_name":"宅急送","freight":349,"max_days":3,"min_days":3}]
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;
    public List<ExpressInfoBean> data;

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
