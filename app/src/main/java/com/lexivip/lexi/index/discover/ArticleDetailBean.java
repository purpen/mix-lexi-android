package com.lexivip.lexi.index.discover;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ArticleDetailBean {
    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        public int audit_status;
        public int browse_count;
        public String channel_name;
        public int comment_count;
        public String content;
        public String cover;
        public int cover_id;
        public int created_at;
        public String description;
        public boolean is_follow;
        public boolean is_user;
        public int like_count;
        public int praise_count;
        public boolean is_praise;
        public int published_at;
        public String refuse_reason;
        public String rid;
        public int status;
        public String store_logo;
        public String store_name;
        public String title;
        public int type;
        public String uid;
        public String user_avator;
        public String user_name;
        public List<DealContentBean> deal_content;

        public static class DealContentBean {
            /**
             * content : 在这个快节奏的社会，每个人都在不同的身份中转换，然而穿搭难免有时候会不太合时宜！一款适用于众多场合的百搭包包是每个女生所追求的！
             * rid : 6152309
             * type : text
             * big_picture : true
             */

            public String content;
            public String rid;
            public String type;
            public boolean big_picture;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.audit_status);
            dest.writeInt(this.browse_count);
            dest.writeString(this.channel_name);
            dest.writeInt(this.comment_count);
            dest.writeString(this.content);
            dest.writeString(this.cover);
            dest.writeInt(this.cover_id);
            dest.writeInt(this.created_at);
            dest.writeString(this.description);
            dest.writeByte(this.is_follow ? (byte) 1 : (byte) 0);
            dest.writeByte(this.is_user ? (byte) 1 : (byte) 0);
            dest.writeInt(this.like_count);
            dest.writeInt(this.praise_count);
            dest.writeByte(this.is_praise ? (byte) 1 : (byte) 0);
            dest.writeInt(this.published_at);
            dest.writeString(this.refuse_reason);
            dest.writeString(this.rid);
            dest.writeInt(this.status);
            dest.writeString(this.store_logo);
            dest.writeString(this.store_name);
            dest.writeString(this.title);
            dest.writeInt(this.type);
            dest.writeString(this.uid);
            dest.writeString(this.user_avator);
            dest.writeString(this.user_name);
            dest.writeList(this.deal_content);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.audit_status = in.readInt();
            this.browse_count = in.readInt();
            this.channel_name = in.readString();
            this.comment_count = in.readInt();
            this.content = in.readString();
            this.cover = in.readString();
            this.cover_id = in.readInt();
            this.created_at = in.readInt();
            this.description = in.readString();
            this.is_follow = in.readByte() != 0;
            this.is_user = in.readByte() != 0;
            this.like_count = in.readInt();
            this.praise_count = in.readInt();
            this.is_praise = in.readByte() != 0;
            this.published_at = in.readInt();
            this.refuse_reason = in.readString();
            this.rid = in.readString();
            this.status = in.readInt();
            this.store_logo = in.readString();
            this.store_name = in.readString();
            this.title = in.readString();
            this.type = in.readInt();
            this.uid = in.readString();
            this.user_avator = in.readString();
            this.user_name = in.readString();
            this.deal_content = new ArrayList<DealContentBean>();
            in.readList(this.deal_content, DealContentBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
