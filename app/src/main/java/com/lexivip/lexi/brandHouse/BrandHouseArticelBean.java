package com.lexivip.lexi.brandHouse;

import com.lexivip.lexi.beans.LifeWillBean;

import java.util.List;

public class BrandHouseArticelBean {
    /**
     * data : {"count":2,"life_records":[{"audit_status":2,"channel_id":4,"channel_name":"手作教学","content":"saf","cover":"http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","cover_id":2,"created_at":1533290214,"description":"还是飞机沙发斯蒂芬但是发生的发生偶师傅师傅","label_id":2,"label_name":"文化","published_at":1533290214,"refuse_reason":null,"rid":1,"status":2,"title":"哈是否哈哈","type":1,"user_avator":"用户头像","user_name":"用户名","browse_count":"浏览数","comment_count":"评论数","is_follow":"是否互相关注","is_follow_store":"是否关注店铺","like_count":"喜欢数","praise_count":"点赞数"}],"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 2
         * life_records : [{"audit_status":2,"channel_id":4,"channel_name":"手作教学","content":"saf","cover":"http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","cover_id":2,"created_at":1533290214,"description":"还是飞机沙发斯蒂芬但是发生的发生偶师傅师傅","label_id":2,"label_name":"文化","published_at":1533290214,"refuse_reason":null,"rid":1,"status":2,"title":"哈是否哈哈","type":1,"user_avator":"用户头像","user_name":"用户名","browse_count":"浏览数","comment_count":"评论数","is_follow":"是否互相关注","is_follow_store":"是否关注店铺","like_count":"喜欢数","praise_count":"点赞数"}]
         * next : false
         * prev : false
         */

        public int count;
        public boolean next;
        public boolean prev;
        public List<LifeWillBean> life_records;


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
