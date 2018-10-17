package com.lexivip.lexi.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserAddressListBean implements Parcelable {

    /**
     * data : [{"area":null,"area_id":null,"city":"北京","city_id":1,"country_id":1,"first_name":"田","full_address":"北京北京大街","full_name":"田帅","is_default":false,"is_from_wx":false,"last_name":null,"mobile":"13278989898","phone":null,"province":"北京","province_id":1,"rid":"5254096781","street_address":"大街","street_address_two":null,"town":null,"town_id":0,"zipcode":null}]
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;
    public List<DataBean> data;

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

    public static class DataBean implements Parcelable {
        /**
         * area : null
         * area_id : null
         * city : 北京
         * city_id : 1
         * country_id : 1
         * first_name : 田
         * full_address : 北京北京大街
         * full_name : 田帅
         * is_default : false
         * is_from_wx : false
         * last_name : null
         * mobile : 13278989898
         * phone : null
         * province : 北京
         * province_id : 1
         * rid : 5254096781
         * street_address : 大街
         * street_address_two : null
         * town : null
         * town_id : 0
         * zipcode : null
         */

        public String area;
        public String area_id;
        public String city;
        public int city_id;
        public int country_id;
        public String first_name;
        public String full_address;
        public String full_name;
        public boolean is_default;
        public boolean is_from_wx;
        public String last_name;
        public String mobile;
        public String phone;
        public String province;
        public int province_id;
        public String rid;
        public String street_address;
        public String street_address_two;
        public String town;
        public int town_id;
        public String zipcode;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.area);
            dest.writeString(this.area_id);
            dest.writeString(this.city);
            dest.writeInt(this.city_id);
            dest.writeInt(this.country_id);
            dest.writeString(this.first_name);
            dest.writeString(this.full_address);
            dest.writeString(this.full_name);
            dest.writeByte(this.is_default ? (byte) 1 : (byte) 0);
            dest.writeByte(this.is_from_wx ? (byte) 1 : (byte) 0);
            dest.writeString(this.last_name);
            dest.writeString(this.mobile);
            dest.writeString(this.phone);
            dest.writeString(this.province);
            dest.writeInt(this.province_id);
            dest.writeString(this.rid);
            dest.writeString(this.street_address);
            dest.writeString(this.street_address_two);
            dest.writeString(this.town);
            dest.writeInt(this.town_id);
            dest.writeString(this.zipcode);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.area = in.readString();
            this.area_id = in.readString();
            this.city = in.readString();
            this.city_id = in.readInt();
            this.country_id = in.readInt();
            this.first_name = in.readString();
            this.full_address = in.readString();
            this.full_name = in.readString();
            this.is_default = in.readByte() != 0;
            this.is_from_wx = in.readByte() != 0;
            this.last_name = in.readString();
            this.mobile = in.readString();
            this.phone = in.readString();
            this.province = in.readString();
            this.province_id = in.readInt();
            this.rid = in.readString();
            this.street_address = in.readString();
            this.street_address_two = in.readString();
            this.town = in.readString();
            this.town_id = in.readInt();
            this.zipcode = in.readString();
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

    public UserAddressListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.status, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeList(this.data);
    }

    protected UserAddressListBean(Parcel in) {
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<UserAddressListBean> CREATOR = new Creator<UserAddressListBean>() {
        @Override
        public UserAddressListBean createFromParcel(Parcel source) {
            return new UserAddressListBean(source);
        }

        @Override
        public UserAddressListBean[] newArray(int size) {
            return new UserAddressListBean[size];
        }
    };
}
