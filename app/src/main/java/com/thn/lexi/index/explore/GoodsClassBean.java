package com.thn.lexi.index.explore;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GoodsClassBean implements Parcelable {

    /**
     * data : {"categories":[{"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":1,"name":"家居","pid":0,"sort_order":4,"status":1},{"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":3,"name":"创意设计","pid":0,"sort_order":3,"status":1},{"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":4,"name":"杯子","pid":0,"sort_order":2,"status":1},{"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":5,"name":"智能硬件","pid":0,"sort_order":1,"status":1}],"count":17,"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     */

    public DataBean data;
    public StatusBean status;

    public static class DataBean implements Parcelable {
        /**
         * categories : [{"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":1,"name":"家居","pid":0,"sort_order":4,"status":1},{"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":3,"name":"创意设计","pid":0,"sort_order":3,"status":1},{"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":4,"name":"杯子","pid":0,"sort_order":2,"status":1},{"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":5,"name":"智能硬件","pid":0,"sort_order":1,"status":1}]
         * count : 17
         */

        public int count;
        public List<CategoriesBean> categories;

        public static class CategoriesBean implements Parcelable {
            /**
             * cover : http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg
             * description :
             * id : 1
             * name : 家居
             * pid : 0
             * sort_order : 4
             * status : 1
             */

            public String cover;
            public String description;
            public String id;
            public String name;
            public int pid;
            public int sort_order;
            public int status;

            public CategoriesBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.cover);
                dest.writeString(this.description);
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeInt(this.pid);
                dest.writeInt(this.sort_order);
                dest.writeInt(this.status);
            }

            protected CategoriesBean(Parcel in) {
                this.cover = in.readString();
                this.description = in.readString();
                this.id = in.readString();
                this.name = in.readString();
                this.pid = in.readInt();
                this.sort_order = in.readInt();
                this.status = in.readInt();
            }

            public static final Creator<CategoriesBean> CREATOR = new Creator<CategoriesBean>() {
                @Override
                public CategoriesBean createFromParcel(Parcel source) {
                    return new CategoriesBean(source);
                }

                @Override
                public CategoriesBean[] newArray(int size) {
                    return new CategoriesBean[size];
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
            dest.writeTypedList(this.categories);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.categories = in.createTypedArrayList(CategoriesBean.CREATOR);
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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
    }

    public GoodsClassBean() {
    }

    protected GoodsClassBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GoodsClassBean> CREATOR = new Parcelable.Creator<GoodsClassBean>() {
        @Override
        public GoodsClassBean createFromParcel(Parcel source) {
            return new GoodsClassBean(source);
        }

        @Override
        public GoodsClassBean[] newArray(int size) {
            return new GoodsClassBean[size];
        }
    };
}
