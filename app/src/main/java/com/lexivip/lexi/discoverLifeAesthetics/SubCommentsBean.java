package com.lexivip.lexi.discoverLifeAesthetics;

import com.lexivip.lexi.beans.CommentBean;

import java.util.List;

public class SubCommentsBean {

    /**
     * data : {"comments":[{"comment_id":7,"content":"评论","created_at":1533095600,"is_praise":false,"pid":2,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"评论用户名"},{"comment_id":8,"content":"评论","created_at":1533095600,"is_praise":false,"pid":2,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"评论用户名"}],"count":4,"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * comments : [{"comment_id":7,"content":"评论","created_at":1533095600,"is_praise":false,"pid":2,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"评论用户名"},{"comment_id":8,"content":"评论","created_at":1533095600,"is_praise":false,"pid":2,"praise_count":0,"user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"评论用户名"}]
         * count : 4
         * next : false
         * prev : false
         */

        public int count;
        public List<CommentBean> comments;

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
