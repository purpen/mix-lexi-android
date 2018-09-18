package com.thn.lexi.orderList;

import com.thn.lexi.order.OrderBean;

import java.util.List;

public class MyOrderListBean {
    public boolean success;
    public DataBean data;
    public class DataBean{
        public int count;
        public String next;
        public Object prev;
        public List<OrderBean> orders;
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
