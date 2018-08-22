package com.thn.lexi.discoverLifeAesthetics;

public class ShowWindowCommentBean {

    /**
     * data : {"comment_id":"评论编号","content":"评论内容","created_at":"评论时间","is_praise":"是否点过赞: true=点过赞,false=未点过赞","pid":"上级评论编号","praise_count":"评论点赞数","user_avatar":"评论用户头像","user_name":"评论用户名"}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * comment_id : 评论编号
         * content : 评论内容
         * created_at : 评论时间
         * is_praise : 是否点过赞: true=点过赞,false=未点过赞
         * pid : 上级评论编号
         * praise_count : 评论点赞数
         * user_avatar : 评论用户头像
         * user_name : 评论用户名
         */

        public String comment_id;
        public String content;
        public String created_at;
        public String is_praise;
        public String pid;
        public String praise_count;
        public String user_avatar;
        public String user_name;
    }

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
