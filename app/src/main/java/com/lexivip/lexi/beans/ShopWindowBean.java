package com.lexivip.lexi.beans;

import java.util.List;

public class ShopWindowBean {
    /**
     * comment_count : 2
     * description : 每一件都是台湾设计手工制作，孩子的皮肤不允许有任何不良物品的接触，保护孩子从小脚丫开始哦！
     * is_expert : false
     * is_follow : false
     * is_like : false
     * is_official : true
     * keywords : ["创意生活","手工制作","品质好物"]
     * like_count : 1
     * product_count : 3
     * product_covers : ["https://s3.lexivip.com/lxServer/1535292866518.jpg","https://s3.lexivip.com/lxServer/1535295159171.jpg","https://s3.lexivip.com/lxServer/1534238331988.jpg"]
     * products : [{"cover":"https://s3.lexivip.com/lxServer/1535292866518.jpg","rid":"8304698571"},{"cover":"https://s3.lexivip.com/lxServer/1535295159171.jpg","rid":"8619478023"},{"cover":"https://s3.lexivip.com/lxServer/1534238331988.jpg","rid":"8156372084"}]
     * rid : 36
     * title : 宝贝的小脚丫
     * uid : 14638907512
     * user_avatar : https://s3.lexivip.com/20181018/4321Ft9xHLYnD7_nq_iOnHbu8yE_Ac3b.jpg
     * user_name : 乐喜
     */
    public int state;
    public int comment_count;
    public long updated_at;
    public String description;
    public boolean is_expert;
    public int followed_status;
    public boolean is_like;
    public boolean is_official;
    public int like_count;
    public int product_count;
    public String rid;
    public String title;
    public String uid;
    public String user_avatar;
    public String user_name;
    public List<String> keywords;
    public List<String> product_covers;
    public List<ProductBean> products;
    public String PAGE_TAG;
}
