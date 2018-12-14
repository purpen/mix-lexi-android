package com.lexivip.lexi.cashMoney;

import android.os.Parcel;
import android.os.Parcelable;

public class CashMoneyBean implements Parcelable {
    /**
     * data : {"rid":"W4567890098","status":1,"actual_amount":10,"receive_target":1,"created_at":2345678567}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    protected CashMoneyBean(Parcel in) {
        data = in.readParcelable(DataBean.class.getClassLoader());
        status = in.readParcelable(StatusBean.class.getClassLoader());
        success = in.readByte() != 0;
    }

    public static final Creator<CashMoneyBean> CREATOR = new Creator<CashMoneyBean>() {
        @Override
        public CashMoneyBean createFromParcel(Parcel in) {
            return new CashMoneyBean(in);
        }

        @Override
        public CashMoneyBean[] newArray(int size) {
            return new CashMoneyBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
        dest.writeParcelable(status, flags);
        dest.writeByte((byte) (success ? 1 : 0));
    }

    public static class DataBean implements Parcelable{
        /**
         * rid : W4567890098
         * status : 1
         * actual_amount : 10
         * receive_target : 1
         * created_at : 2345678567
         */

        public String rid;
        public int status;
        public int actual_amount;
        public int receive_target;
        public long created_at;

        protected DataBean(Parcel in) {
            rid = in.readString();
            status = in.readInt();
            actual_amount = in.readInt();
            receive_target = in.readInt();
            created_at = in.readLong();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(rid);
            dest.writeInt(status);
            dest.writeInt(actual_amount);
            dest.writeInt(receive_target);
            dest.writeLong(created_at);
        }
    }

    public static class StatusBean implements Parcelable{
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;

        protected StatusBean(Parcel in) {
            code = in.readInt();
            message = in.readString();
        }

        public static final Creator<StatusBean> CREATOR = new Creator<StatusBean>() {
            @Override
            public StatusBean createFromParcel(Parcel in) {
                return new StatusBean(in);
            }

            @Override
            public StatusBean[] newArray(int size) {
                return new StatusBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(code);
            dest.writeString(message);
        }
    }
}
