package com.lexivip.lexi.publishShopWindow;

import com.lexivip.lexi.beans.ShopWindowBean;

public class PublishShopWindowBean {
    public ShopWindowBean data;
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
