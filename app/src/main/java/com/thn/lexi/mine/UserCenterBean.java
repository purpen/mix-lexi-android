package com.thn.lexi.mine;

import android.os.Parcel;
import android.os.Parcelable;

public class UserCenterBean {

    /**
     * data : {"ID":"17048395612","about_me":"肚子好饿","avatar":"http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png","followed_stores_counts":2,"followed_users_counts":0,"fans_counts":0,"store_phases":1,"user_like_counts":0,"username":"张飞","wish_list_counts":0}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * ID : 17048395612
         * about_me : 肚子好饿
         * avatar : http://0.0.0.0:9000/_uploads/photos/static/img/default2-logo-180x180.png
         * followed_stores_counts : 2
         * followed_users_counts : 0
         * fans_counts : 0
         * store_phases : 1
         * user_like_counts : 0
         * username : 张飞
         * wish_list_counts : 0
         */

        public String ID;
        public String about_me;
        public String avatar;
        public String followed_stores_counts;
        public String followed_users_counts;
        public String fans_counts;
        public String store_phases;
        public String user_like_counts;
        public String username;
        public String wish_list_counts;

        public DataBean(Parcel in) {
            ID = in.readString();
            about_me = in.readString();
            avatar = in.readString();
            followed_stores_counts = in.readString();
            followed_users_counts = in.readString();
            fans_counts = in.readString();
            store_phases = in.readString();
            user_like_counts = in.readString();
            username = in.readString();
            wish_list_counts = in.readString();
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
            dest.writeString(ID);
            dest.writeString(about_me);
            dest.writeString(avatar);
            dest.writeString(followed_stores_counts);
            dest.writeString(followed_users_counts);
            dest.writeString(fans_counts);
            dest.writeString(store_phases);
            dest.writeString(user_like_counts);
            dest.writeString(username);
            dest.writeString(wish_list_counts);
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
