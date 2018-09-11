package com.thn.lexi.shopCart;

import com.thn.lexi.beans.ProductBean;

import java.util.List;

public class WishOrderListBean {

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {

        public List<ProductBean> products;

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
