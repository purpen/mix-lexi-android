package com.lexivip.lexi.order;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpressInfoBean implements Parcelable {
    public String express_code;
    public String express_id;
    public String express_name;
    public double freight;
    public boolean is_default;
    public int max_days;
    public int min_days;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.express_code);
        dest.writeString(this.express_id);
        dest.writeString(this.express_name);
        dest.writeDouble(this.freight);
        dest.writeByte(this.is_default ? (byte) 1 : (byte) 0);
        dest.writeInt(this.max_days);
        dest.writeInt(this.min_days);
    }

    public ExpressInfoBean() {
    }

    protected ExpressInfoBean(Parcel in) {
        this.express_code = in.readString();
        this.express_id = in.readString();
        this.express_name = in.readString();
        this.freight = in.readDouble();
        this.is_default = in.readByte() != 0;
        this.max_days = in.readInt();
        this.min_days = in.readInt();
    }

    public static final Parcelable.Creator<ExpressInfoBean> CREATOR = new Parcelable.Creator<ExpressInfoBean>() {
        @Override
        public ExpressInfoBean createFromParcel(Parcel source) {
            return new ExpressInfoBean(source);
        }

        @Override
        public ExpressInfoBean[] newArray(int size) {
            return new ExpressInfoBean[size];
        }
    };
}
