package com.thn.lexi.user.areacode;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CountryAreaCodeBean implements Parcelable {

    /**
     * data : {"area_codes":[{"areacode":"+86","en_name":"china","id":1,"name":"中国"},{"areacode":"+886","en_name":"TaiWan","id":2,"name":"中国台湾"},{"areacode":"+852","en_name":"HongKong","id":3,"name":"中国香港"},{"areacode":"+863","en_name":"Macao","id":4,"name":"中国澳门"}],"count":4,"next":null,"prev":null}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * area_codes : [{"areacode":"+86","en_name":"china","id":1,"name":"中国"},{"areacode":"+886","en_name":"TaiWan","id":2,"name":"中国台湾"},{"areacode":"+852","en_name":"HongKong","id":3,"name":"中国香港"},{"areacode":"+863","en_name":"Macao","id":4,"name":"中国澳门"}]
         * count : 4
         * next : null
         * prev : null
         */

        public int count;
        public List<AreaCodesBean> area_codes;

        public static class AreaCodesBean implements Parcelable {
            /**
             * areacode : +86
             * en_name : china
             * id : 1
             * name : 中国
             */

            public String areacode;
            public String en_name;
            public int id;
            public String name;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.areacode);
                dest.writeString(this.en_name);
                dest.writeInt(this.id);
                dest.writeString(this.name);
            }

            public AreaCodesBean() {
            }

            protected AreaCodesBean(Parcel in) {
                this.areacode = in.readString();
                this.en_name = in.readString();
                this.id = in.readInt();
                this.name = in.readString();
            }

            public static final Creator<AreaCodesBean> CREATOR = new Creator<AreaCodesBean>() {
                @Override
                public AreaCodesBean createFromParcel(Parcel source) {
                    return new AreaCodesBean(source);
                }

                @Override
                public AreaCodesBean[] newArray(int size) {
                    return new AreaCodesBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.count);
            dest.writeList(this.area_codes);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.area_codes = new ArrayList<AreaCodesBean>();
            in.readList(this.area_codes, AreaCodesBean.class.getClassLoader());
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

    public CountryAreaCodeBean() {
    }

    protected CountryAreaCodeBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CountryAreaCodeBean> CREATOR = new Parcelable.Creator<CountryAreaCodeBean>() {
        @Override
        public CountryAreaCodeBean createFromParcel(Parcel source) {
            return new CountryAreaCodeBean(source);
        }

        @Override
        public CountryAreaCodeBean[] newArray(int size) {
            return new CountryAreaCodeBean[size];
        }
    };
}
