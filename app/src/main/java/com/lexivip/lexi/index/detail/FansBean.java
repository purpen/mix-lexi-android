package com.lexivip.lexi.index.detail;

import com.lexivip.lexi.beans.UserBean;

import java.util.List;

public class FansBean {
    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public int count;
        public List<UserBean> user_fans;
    }

    public static class StatusBean {
        public int code;
        public String message;
    }
}
