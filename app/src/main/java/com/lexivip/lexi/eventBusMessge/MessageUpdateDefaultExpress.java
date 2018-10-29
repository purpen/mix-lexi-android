package com.lexivip.lexi.eventBusMessge;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageUpdateDefaultExpress implements Parcelable {
    public String store_rid;
    public String product_rid;
    public String express_id;
    public String fid;
    public int max_days;
    public int min_days;

    public MessageUpdateDefaultExpress() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.store_rid);
        dest.writeString(this.product_rid);
        dest.writeString(this.express_id);
        dest.writeString(this.fid);
        dest.writeInt(this.max_days);
        dest.writeInt(this.min_days);
    }

    protected MessageUpdateDefaultExpress(Parcel in) {
        this.store_rid = in.readString();
        this.product_rid = in.readString();
        this.express_id = in.readString();
        this.fid = in.readString();
        this.max_days = in.readInt();
        this.min_days = in.readInt();
    }

    public static final Creator<MessageUpdateDefaultExpress> CREATOR = new Creator<MessageUpdateDefaultExpress>() {
        @Override
        public MessageUpdateDefaultExpress createFromParcel(Parcel source) {
            return new MessageUpdateDefaultExpress(source);
        }

        @Override
        public MessageUpdateDefaultExpress[] newArray(int size) {
            return new MessageUpdateDefaultExpress[size];
        }
    };
}
