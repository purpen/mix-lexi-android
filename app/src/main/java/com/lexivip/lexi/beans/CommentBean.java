package com.lexivip.lexi.beans;

import java.util.List;

public class CommentBean {

    /**
     * comment_id : 89
     * content : KTV图
     * created_at : 1540218409
     * is_praise : false
     * pid : 0
     * praise_count : 0
     * sub_comment_count : 1
     * sub_comments : [{"comment_id":90,"content":"几率","created_at":1540218415,"is_praise":false,"pid":89,"praise_count":0,"user_avatar":"https://s3.lexivip.com/wx_avatar/oDlWK5VU-UKnUrnL1nC97Lv7np4Q","user_name":"乐喜-让有趣变得流行"}]
     * user_avatar : https://s3.lexivip.com/wx_avatar/oDlWK5VU-UKnUrnL1nC97Lv7np4Q
     * user_name : 乐喜-让有趣变得流行
     */

    public String comment_id;
    public String content;
    public long created_at;
    public boolean is_praise;
    public String pid;
    public int praise_count;
    public int sub_comment_count;
    public String user_avatar;
    public String user_name;
    public List<CommentBean> sub_comments;
}
