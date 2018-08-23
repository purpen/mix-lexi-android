package com.thn.lexi.discoverLifeAesthetics;

import java.util.List;

public class CommentBean {

    /**
     * comment_id : 7
     * content : 评论
     * created_at : 1533095600
     * is_praise : false
     * pid : 2
     * praise_count : 0
     * user_avatar : http://kg.erp.taihuoniao.com/static/img/default-logo.png
     * user_name : 评论用户名
     */

    public String comment_id;
    public String content;
    public long created_at;
    public boolean is_praise;
    public String pid;
    public int praise_count;
    public String user_avatar;
    public String user_name;
    public List<CommentBean> sub_comments;
}
