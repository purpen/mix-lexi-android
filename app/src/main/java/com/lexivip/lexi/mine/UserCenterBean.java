package com.lexivip.lexi.mine;

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

        public String uid;
        public String about_me;
        public String avatar;
        public int followed_status;
        public String followed_stores_counts;
        public String followed_users_counts;
        public String fans_counts;
        public String store_phases;
        public String user_like_counts;
        public String username;
        public String wish_list_counts;

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.uid);
            dest.writeString(this.about_me);
            dest.writeString(this.avatar);
            dest.writeInt(this.followed_status);
            dest.writeString(this.followed_stores_counts);
            dest.writeString(this.followed_users_counts);
            dest.writeString(this.fans_counts);
            dest.writeString(this.store_phases);
            dest.writeString(this.user_like_counts);
            dest.writeString(this.username);
            dest.writeString(this.wish_list_counts);
        }

        protected DataBean(Parcel in) {
            this.uid = in.readString();
            this.about_me = in.readString();
            this.avatar = in.readString();
            this.followed_status = in.readInt();
            this.followed_stores_counts = in.readString();
            this.followed_users_counts = in.readString();
            this.fans_counts = in.readString();
            this.store_phases = in.readString();
            this.user_like_counts = in.readString();
            this.username = in.readString();
            this.wish_list_counts = in.readString();
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

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
