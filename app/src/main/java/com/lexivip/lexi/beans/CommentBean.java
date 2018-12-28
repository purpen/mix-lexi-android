package com.lexivip.lexi.beans;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class CommentBean implements Parcelable {

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
    public int subCommentPage=1;
    public String comment_id;
    public String content;
    public long created_at;
    public boolean is_praise;
    public String pid;
    public String uid;
    public int praise_count;
    public int sub_comment_count;
    public String user_avatar;
    public String user_name;
    public String reply_user_name;
    public String reply_user_uid;
    public List<CommentBean> sub_comments;

    public CommentBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.subCommentPage);
        dest.writeString(this.comment_id);
        dest.writeString(this.content);
        dest.writeLong(this.created_at);
        dest.writeByte(this.is_praise ? (byte) 1 : (byte) 0);
        dest.writeString(this.pid);
        dest.writeString(this.uid);
        dest.writeInt(this.praise_count);
        dest.writeInt(this.sub_comment_count);
        dest.writeString(this.user_avatar);
        dest.writeString(this.user_name);
        dest.writeString(this.reply_user_name);
        dest.writeString(this.reply_user_uid);
        dest.writeTypedList(this.sub_comments);
    }

    protected CommentBean(Parcel in) {
        this.subCommentPage = in.readInt();
        this.comment_id = in.readString();
        this.content = in.readString();
        this.created_at = in.readLong();
        this.is_praise = in.readByte() != 0;
        this.pid = in.readString();
        this.uid = in.readString();
        this.praise_count = in.readInt();
        this.sub_comment_count = in.readInt();
        this.user_avatar = in.readString();
        this.user_name = in.readString();
        this.reply_user_name = in.readString();
        this.reply_user_uid = in.readString();
        this.sub_comments = in.createTypedArrayList(CommentBean.CREATOR);
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel source) {
            return new CommentBean(source);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };
}
