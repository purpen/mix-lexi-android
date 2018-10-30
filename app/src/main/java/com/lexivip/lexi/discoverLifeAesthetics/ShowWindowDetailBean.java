package com.lexivip.lexi.discoverLifeAesthetics;

import android.os.Parcel;
import android.os.Parcelable;

import com.lexivip.lexi.beans.CommentBean;
import com.lexivip.lexi.beans.ProductBean;

import java.util.List;

public class ShowWindowDetailBean implements Parcelable {

    /**
     * data : {"comment_count":2,"comments":[{"comment_id":89,"content":"KTV图","created_at":1540218409,"is_praise":false,"pid":0,"praise_count":0,"sub_comment_count":1,"sub_comments":[{"comment_id":90,"content":"几率","created_at":1540218415,"is_praise":false,"pid":89,"praise_count":0,"user_avatar":"https://s3.lexivip.com/wx_avatar/oDlWK5VU-UKnUrnL1nC97Lv7np4Q","user_name":"乐喜-让有趣变得流行"}],"user_avatar":"https://s3.lexivip.com/wx_avatar/oDlWK5VU-UKnUrnL1nC97Lv7np4Q","user_name":"乐喜-让有趣变得流行"}],"current_page":1,"description":"每一件都是台湾设计手工制作，孩子的皮肤不允许有任何不良物品的接触，保护孩子从小脚丫开始哦！","is_expert":false,"is_follow":false,"is_like":true,"is_official":true,"keywords":["创意生活","手工制作","品质好物"],"like_count":2,"product_count":3,"product_covers":["https://s3.lexivip.com/lxServer/1535292866518.jpg","https://s3.lexivip.com/lxServer/1535295159171.jpg","https://s3.lexivip.com/lxServer/1534238331988.jpg"],"products":[{"bgcover":"https://s3.lexivip.com/lxServer/1535292322554.jpg","category_id":311,"city":"内湖区","country":"中国台湾","cover":"https://s3.lexivip.com/lxServer/1535292866518.jpg","cover_id":20225,"custom_details":null,"delivery_city":"内湖区","delivery_country":"中国台湾","delivery_country_id":2,"delivery_province":"台北市","distribution_type":0,"fans_count":1,"features":null,"id_code":"SKC01-MU","is_custom_made":false,"is_custom_service":false,"is_distributed":false,"is_free_postage":false,"is_made_holiday":false,"is_sold_out":false,"like_count":3,"made_cycle":0,"material_id":null,"material_name":"","max_price":69,"max_sale_price":0,"min_price":69,"min_sale_price":0,"modes":["默认 默认"],"name":"樱桃心防滑2分之1童袜","province":"台北市","published_at":0,"rid":"8304698571","second_category_id":309,"status":1,"sticked":false,"store_logo":"https://s3.lexivip.com/lxServer/1535281892454.jpg","store_name":"sokker®","store_rid":"93271854","style_id":0,"style_name":"","tag_line":"sokker®不只是保护您的双脚，也是您出门的最佳配角。","top_category_id":290,"total_stock":30,"town":""},{"bgcover":"https://s3.lexivip.com/lxServer/1535292322554.jpg","category_id":311,"city":"内湖区","country":"中国台湾","cover":"https://s3.lexivip.com/lxServer/1535295159171.jpg","cover_id":20243,"custom_details":null,"delivery_city":"内湖区","delivery_country":"中国台湾","delivery_country_id":2,"delivery_province":"台北市","distribution_type":0,"fans_count":1,"features":null,"id_code":"SKA16-PPc","is_custom_made":false,"is_custom_service":false,"is_distributed":false,"is_free_postage":false,"is_made_holiday":false,"is_sold_out":false,"like_count":4,"made_cycle":0,"material_id":null,"material_name":"","max_price":75,"max_sale_price":0,"min_price":75,"min_sale_price":0,"modes":["紫色 14-15cm","紫色 15-17cm"],"name":"太空星球防滑4分之3童袜","province":"台北市","published_at":0,"rid":"8619478023","second_category_id":309,"status":1,"sticked":false,"store_logo":"https://s3.lexivip.com/lxServer/1535281892454.jpg","store_name":"sokker®","store_rid":"93271854","style_id":0,"style_name":"","tag_line":"sokker®不只是保护您的双脚，也是您出门的最佳配角。","top_category_id":290,"total_stock":60,"town":""},{"bgcover":"https://s3.lexivip.com/lxServer/1534929659070.jpg","category_id":310,"city":"北屯区","country":"中国台湾","cover":"https://s3.lexivip.com/lxServer/1534238331988.jpg","cover_id":18635,"custom_details":null,"delivery_city":"北屯区","delivery_country":"中国台湾","delivery_country_id":2,"delivery_province":"台中市","distribution_type":0,"fans_count":0,"features":null,"id_code":"BO1710D","is_custom_made":false,"is_custom_service":false,"is_distributed":false,"is_free_postage":true,"is_made_holiday":false,"is_sold_out":false,"like_count":3,"made_cycle":0,"material_id":null,"material_name":"","max_price":270,"max_sale_price":240,"min_price":270,"min_sale_price":240,"modes":["粉色 14","粉色 15","粉色 16","粉色 17","粉色 18","粉色 19"],"name":"插畫娃娃童鞋-粉色/草莓小紅帽與大野狼","province":"台中市","published_at":0,"rid":"8156372084","second_category_id":309,"status":1,"sticked":false,"store_logo":"https://s3.lexivip.com/lxServer/1534237714826.jpg","store_name":"BoingBoing故事鞋","store_rid":"90693728","style_id":0,"style_name":"","tag_line":"把故事穿在腳上, 妳與孩子一起享受「童話故事的美好生活」","top_category_id":290,"total_stock":7,"town":""}],"remain_count":0,"rid":36,"title":"宝贝的小脚丫","total_count":1,"uid":"14638907512","updated_at":1540455156,"user_avatar":"https://s3.lexivip.com/20181018/4321Ft9xHLYnD7_nq_iOnHbu8yE_Ac3b.jpg","user_name":"乐喜"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * comment_count : 2
         * comments : [{"comment_id":89,"content":"KTV图","created_at":1540218409,"is_praise":false,"pid":0,"praise_count":0,"sub_comment_count":1,"sub_comments":[{"comment_id":90,"content":"几率","created_at":1540218415,"is_praise":false,"pid":89,"praise_count":0,"user_avatar":"https://s3.lexivip.com/wx_avatar/oDlWK5VU-UKnUrnL1nC97Lv7np4Q","user_name":"乐喜-让有趣变得流行"}],"user_avatar":"https://s3.lexivip.com/wx_avatar/oDlWK5VU-UKnUrnL1nC97Lv7np4Q","user_name":"乐喜-让有趣变得流行"}]
         * current_page : 1
         * description : 每一件都是台湾设计手工制作，孩子的皮肤不允许有任何不良物品的接触，保护孩子从小脚丫开始哦！
         * is_expert : false
         * is_follow : false
         * is_like : true
         * is_official : true
         * keywords : ["创意生活","手工制作","品质好物"]
         * like_count : 2
         * product_count : 3
         * product_covers : ["https://s3.lexivip.com/lxServer/1535292866518.jpg","https://s3.lexivip.com/lxServer/1535295159171.jpg","https://s3.lexivip.com/lxServer/1534238331988.jpg"]
         * products : [{"bgcover":"https://s3.lexivip.com/lxServer/1535292322554.jpg","category_id":311,"city":"内湖区","country":"中国台湾","cover":"https://s3.lexivip.com/lxServer/1535292866518.jpg","cover_id":20225,"custom_details":null,"delivery_city":"内湖区","delivery_country":"中国台湾","delivery_country_id":2,"delivery_province":"台北市","distribution_type":0,"fans_count":1,"features":null,"id_code":"SKC01-MU","is_custom_made":false,"is_custom_service":false,"is_distributed":false,"is_free_postage":false,"is_made_holiday":false,"is_sold_out":false,"like_count":3,"made_cycle":0,"material_id":null,"material_name":"","max_price":69,"max_sale_price":0,"min_price":69,"min_sale_price":0,"modes":["默认 默认"],"name":"樱桃心防滑2分之1童袜","province":"台北市","published_at":0,"rid":"8304698571","second_category_id":309,"status":1,"sticked":false,"store_logo":"https://s3.lexivip.com/lxServer/1535281892454.jpg","store_name":"sokker®","store_rid":"93271854","style_id":0,"style_name":"","tag_line":"sokker®不只是保护您的双脚，也是您出门的最佳配角。","top_category_id":290,"total_stock":30,"town":""},{"bgcover":"https://s3.lexivip.com/lxServer/1535292322554.jpg","category_id":311,"city":"内湖区","country":"中国台湾","cover":"https://s3.lexivip.com/lxServer/1535295159171.jpg","cover_id":20243,"custom_details":null,"delivery_city":"内湖区","delivery_country":"中国台湾","delivery_country_id":2,"delivery_province":"台北市","distribution_type":0,"fans_count":1,"features":null,"id_code":"SKA16-PPc","is_custom_made":false,"is_custom_service":false,"is_distributed":false,"is_free_postage":false,"is_made_holiday":false,"is_sold_out":false,"like_count":4,"made_cycle":0,"material_id":null,"material_name":"","max_price":75,"max_sale_price":0,"min_price":75,"min_sale_price":0,"modes":["紫色 14-15cm","紫色 15-17cm"],"name":"太空星球防滑4分之3童袜","province":"台北市","published_at":0,"rid":"8619478023","second_category_id":309,"status":1,"sticked":false,"store_logo":"https://s3.lexivip.com/lxServer/1535281892454.jpg","store_name":"sokker®","store_rid":"93271854","style_id":0,"style_name":"","tag_line":"sokker®不只是保护您的双脚，也是您出门的最佳配角。","top_category_id":290,"total_stock":60,"town":""},{"bgcover":"https://s3.lexivip.com/lxServer/1534929659070.jpg","category_id":310,"city":"北屯区","country":"中国台湾","cover":"https://s3.lexivip.com/lxServer/1534238331988.jpg","cover_id":18635,"custom_details":null,"delivery_city":"北屯区","delivery_country":"中国台湾","delivery_country_id":2,"delivery_province":"台中市","distribution_type":0,"fans_count":0,"features":null,"id_code":"BO1710D","is_custom_made":false,"is_custom_service":false,"is_distributed":false,"is_free_postage":true,"is_made_holiday":false,"is_sold_out":false,"like_count":3,"made_cycle":0,"material_id":null,"material_name":"","max_price":270,"max_sale_price":240,"min_price":270,"min_sale_price":240,"modes":["粉色 14","粉色 15","粉色 16","粉色 17","粉色 18","粉色 19"],"name":"插畫娃娃童鞋-粉色/草莓小紅帽與大野狼","province":"台中市","published_at":0,"rid":"8156372084","second_category_id":309,"status":1,"sticked":false,"store_logo":"https://s3.lexivip.com/lxServer/1534237714826.jpg","store_name":"BoingBoing故事鞋","store_rid":"90693728","style_id":0,"style_name":"","tag_line":"把故事穿在腳上, 妳與孩子一起享受「童話故事的美好生活」","top_category_id":290,"total_stock":7,"town":""}]
         * remain_count : 0
         * rid : 36
         * title : 宝贝的小脚丫
         * total_count : 1
         * uid : 14638907512
         * updated_at : 1540455156
         * user_avatar : https://s3.lexivip.com/20181018/4321Ft9xHLYnD7_nq_iOnHbu8yE_Ac3b.jpg
         * user_name : 乐喜
         */

        public int comment_count;
        public int current_page;
        public String description;
        public boolean is_expert;
        public boolean is_follow;
        public boolean is_like;
        public boolean is_official;
        public int like_count;
        public int product_count;
        public int remain_count;
        public String rid;
        public String title;
        public int total_count;
        public String uid;
        public long updated_at;
        public String user_avatar;
        public String user_name;
        public List<CommentBean> comments;
        public List<String> keywords;
        public List<String> product_covers;
        public List<ProductBean> products;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.comment_count);
            dest.writeInt(this.current_page);
            dest.writeString(this.description);
            dest.writeByte(this.is_expert ? (byte) 1 : (byte) 0);
            dest.writeByte(this.is_follow ? (byte) 1 : (byte) 0);
            dest.writeByte(this.is_like ? (byte) 1 : (byte) 0);
            dest.writeByte(this.is_official ? (byte) 1 : (byte) 0);
            dest.writeInt(this.like_count);
            dest.writeInt(this.product_count);
            dest.writeInt(this.remain_count);
            dest.writeString(this.rid);
            dest.writeString(this.title);
            dest.writeInt(this.total_count);
            dest.writeString(this.uid);
            dest.writeLong(this.updated_at);
            dest.writeString(this.user_avatar);
            dest.writeString(this.user_name);
            dest.writeTypedList(this.comments);
            dest.writeStringList(this.keywords);
            dest.writeStringList(this.product_covers);
            dest.writeTypedList(this.products);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.comment_count = in.readInt();
            this.current_page = in.readInt();
            this.description = in.readString();
            this.is_expert = in.readByte() != 0;
            this.is_follow = in.readByte() != 0;
            this.is_like = in.readByte() != 0;
            this.is_official = in.readByte() != 0;
            this.like_count = in.readInt();
            this.product_count = in.readInt();
            this.remain_count = in.readInt();
            this.rid = in.readString();
            this.title = in.readString();
            this.total_count = in.readInt();
            this.uid = in.readString();
            this.updated_at = in.readLong();
            this.user_avatar = in.readString();
            this.user_name = in.readString();
            this.comments = in.createTypedArrayList(CommentBean.CREATOR);
            this.keywords = in.createStringArrayList();
            this.product_covers = in.createStringArrayList();
            this.products = in.createTypedArrayList(ProductBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    public static class StatusBean implements Parcelable {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.code);
            dest.writeString(this.message);
        }

        public StatusBean() {
        }

        protected StatusBean(Parcel in) {
            this.code = in.readInt();
            this.message = in.readString();
        }

        public static final Creator<StatusBean> CREATOR = new Creator<StatusBean>() {
            @Override
            public StatusBean createFromParcel(Parcel source) {
                return new StatusBean(source);
            }

            @Override
            public StatusBean[] newArray(int size) {
                return new StatusBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
        dest.writeParcelable(this.status, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public ShowWindowDetailBean() {
    }

    protected ShowWindowDetailBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ShowWindowDetailBean> CREATOR = new Parcelable.Creator<ShowWindowDetailBean>() {
        @Override
        public ShowWindowDetailBean createFromParcel(Parcel source) {
            return new ShowWindowDetailBean(source);
        }

        @Override
        public ShowWindowDetailBean[] newArray(int size) {
            return new ShowWindowDetailBean[size];
        }
    };
}
