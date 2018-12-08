package com.lexivip.lexi.brandHouse;

import android.os.Parcel;
import android.os.Parcelable;

public class BrandHouseBean implements Parcelable{

    /**
     * data : {"city":"北京","country":"中国","is_followed":false,"fans_count":0,"life_record_count":3,"logo":"http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png","bgcover":"","town":"","delivery_country":"","delivery_province":"","delivery_city":"","name":"","product_count":0,"province":"北京","rid":"97958360","tag_line":"sdjkf","created_at":12345612054}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    protected BrandHouseBean(Parcel in) {
        data = in.readParcelable(DataBean.class.getClassLoader());
        success = in.readByte() != 0;
    }

    public static final Creator<BrandHouseBean> CREATOR = new Creator<BrandHouseBean>() {
        @Override
        public BrandHouseBean createFromParcel(Parcel in) {
            return new BrandHouseBean(in);
        }

        @Override
        public BrandHouseBean[] newArray(int size) {
            return new BrandHouseBean[size];
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

    public static class DataBean implements Parcelable {
        /**
         * city : 北京
         * country : 中国
         * is_followed : false
         * fans_count : 0
         * life_record_count : 3
         * logo : http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png
         * bgcover :
         * town :
         * delivery_country :
         * delivery_province :
         * delivery_city :
         * name :
         * product_count : 0
         * province : 北京
         * rid : 97958360
         * tag_line : sdjkf
         * created_at : 12345612054
         */

        public String city;
        public String country;
        public boolean is_followed;
        public int fans_count;
        public int life_record_count;
        public String logo;
        public String bgcover;
        public String town;
        public String delivery_country;
        public String delivery_province;
        public String delivery_city;
        public String name;
        public int product_count;
        public String province;
        public String rid;
        public String tag_line;
        public long created_at;
        public boolean has_qualification;

        protected DataBean(Parcel in) {
            city = in.readString();
            country = in.readString();
            is_followed = in.readByte() != 0;
            fans_count = in.readInt();
            life_record_count = in.readInt();
            logo = in.readString();
            bgcover = in.readString();
            town = in.readString();
            delivery_country = in.readString();
            delivery_province = in.readString();
            delivery_city = in.readString();
            name = in.readString();
            product_count = in.readInt();
            province = in.readString();
            rid = in.readString();
            tag_line = in.readString();
            created_at = in.readLong();
            has_qualification=in.readByte() != 0;
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
            dest.writeString(city);
            dest.writeString(country);
            dest.writeByte((byte) (is_followed ? 1 : 0));
            dest.writeInt(fans_count);
            dest.writeInt(life_record_count);
            dest.writeString(logo);
            dest.writeString(bgcover);
            dest.writeString(town);
            dest.writeString(delivery_country);
            dest.writeString(delivery_province);
            dest.writeString(delivery_city);
            dest.writeString(name);
            dest.writeInt(product_count);
            dest.writeString(province);
            dest.writeString(rid);
            dest.writeString(tag_line);
            dest.writeLong(created_at);
            dest.writeByte((byte) (has_qualification ? 1 : 0));
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
