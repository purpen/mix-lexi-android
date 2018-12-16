package com.lexivip.lexi.cashMoney;

import android.os.Parcel;
import android.os.Parcelable;

public class CashMoneyBean implements Parcelable {

    /**
     * data : {"rid":"W4567890098","status":1,"actual_amount":10,"receive_target":1,"user_account":121321312,"created_at":2345678567,"not_cash":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    protected CashMoneyBean(Parcel in) {
        data = in.readParcelable(DataBean.class.getClassLoader());
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
        dest.writeByte((byte) (success ? 1 : 0));
    }

    public static class DataBean implements Parcelable{
        /**
         * rid : W4567890098
         * status : 1
         * actual_amount : 10
         * receive_target : 1
         * user_account : 121321312
         * created_at : 2345678567
         * not_cash : false
         */

        public String rid;
        public int status;
        public String actual_amount;
        public int receive_target;
        public String user_account;
        public long created_at;
        public boolean not_cash;

        protected DataBean(Parcel in) {
            rid = in.readString();
            status = in.readInt();
            actual_amount = in.readString();
            receive_target = in.readInt();
            user_account = in.readString();
            created_at = in.readLong();
            not_cash = in.readByte() != 0;
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
            dest.writeString(actual_amount);
            dest.writeInt(receive_target);
            dest.writeString(user_account);
            dest.writeLong(created_at);
            dest.writeByte((byte) (not_cash ? 1 : 0));
        }
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
