package com.thn.lexi;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageUpdateDefaultExpress implements Parcelable {
    public String store_rid;
    public String product_rid;
    public String express_id;
    public String fid;

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
    }

    public MessageUpdateDefaultExpress() {
    }

    protected MessageUpdateDefaultExpress(Parcel in) {
        this.store_rid = in.readString();
        this.product_rid = in.readString();
        this.express_id = in.readString();
        this.fid = in.readString();
    }

    public static final Parcelable.Creator<MessageUpdateDefaultExpress> CREATOR = new Parcelable.Creator<MessageUpdateDefaultExpress>() {
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
