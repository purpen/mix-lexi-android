package com.lexivip.lexi.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserBean implements Parcelable {
    /**
     * about_me : 好人
     * area :
     * area_id : 0
     * areacode : +86
     * avatar : http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png
     * avatar_id : 1
     * city : 北京
     * city_id : 1
     * country : 中国
     * country_id : 1
     * created_at : 1532055457
     * date : 2000-02-02
     * description : null
     * email : 13001179400
     * followed_status : 0
     * gender : 0
     * is_distributor : false
     * last_seen : 1532055457
     * mail : 4568794@qq.com
     * master_uid : 2
     * mobile : +86-13645647894
     * phone :
     * province : 北京
     * province_id : 1
     * street_address : 天安门
     * uid : 17048395612
     * user_areacode : ["+86","13645647894"]
     * username : 张飞
     */

    public String about_me;
    public String area;
    public int area_id;
    public String areacode;
    public String avatar;
    public int avatar_id;
    public String city;
    public int city_id;
    public String country;
    public int country_id;
    public int created_at;
    public String date;
    public String email;
    public int followed_status;
    public int follow_status;
    public int gender;
    public boolean is_distributor;
    public int last_seen;
    public String mail;
    public int master_uid;
    public String mobile;
    public String phone;
    public String province;
    public int province_id;
    public String street_address;
    public String uid;
    public String username;
    public List<String> user_areacode;
    public int wish_list_counts;
    public int user_like_counts;

    public UserBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.about_me);
        dest.writeString(this.area);
        dest.writeInt(this.area_id);
        dest.writeString(this.areacode);
        dest.writeString(this.avatar);
        dest.writeInt(this.avatar_id);
        dest.writeString(this.city);
        dest.writeInt(this.city_id);
        dest.writeString(this.country);
        dest.writeInt(this.country_id);
        dest.writeInt(this.created_at);
        dest.writeString(this.date);
        dest.writeString(this.email);
        dest.writeInt(this.followed_status);
        dest.writeInt(this.gender);
        dest.writeByte(this.is_distributor ? (byte) 1 : (byte) 0);
        dest.writeInt(this.last_seen);
        dest.writeString(this.mail);
        dest.writeInt(this.master_uid);
        dest.writeString(this.mobile);
        dest.writeString(this.phone);
        dest.writeString(this.province);
        dest.writeInt(this.province_id);
        dest.writeString(this.street_address);
        dest.writeString(this.uid);
        dest.writeString(this.username);
        dest.writeStringList(this.user_areacode);
        dest.writeInt(this.wish_list_counts);
    }

    protected UserBean(Parcel in) {
        this.about_me = in.readString();
        this.area = in.readString();
        this.area_id = in.readInt();
        this.areacode = in.readString();
        this.avatar = in.readString();
        this.avatar_id = in.readInt();
        this.city = in.readString();
        this.city_id = in.readInt();
        this.country = in.readString();
        this.country_id = in.readInt();
        this.created_at = in.readInt();
        this.date = in.readString();
        this.email = in.readString();
        this.followed_status = in.readInt();
        this.gender = in.readInt();
        this.is_distributor = in.readByte() != 0;
        this.last_seen = in.readInt();
        this.mail = in.readString();
        this.master_uid = in.readInt();
        this.mobile = in.readString();
        this.phone = in.readString();
        this.province = in.readString();
        this.province_id = in.readInt();
        this.street_address = in.readString();
        this.uid = in.readString();
        this.username = in.readString();
        this.user_areacode = in.createStringArrayList();
        this.wish_list_counts = in.readInt();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
