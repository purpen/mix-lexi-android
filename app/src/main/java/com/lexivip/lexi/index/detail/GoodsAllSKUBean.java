package com.lexivip.lexi.index.detail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GoodsAllSKUBean implements Parcelable {

    /**
     * data : {"colors":[{"name":"深红色","valid":true}],"items":[{"commission_price":18.52,"commission_rate":1.5,"cover":"http://xxx/_uploads/photos/180302/496886303714e7e.jpg","mode":"深红色","price":"45.00","product_name":"测试供应商方式","rid":"118130473518","s_color":"深红色","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":8},{"commission_price":18.52,"commission_rate":1.5,"cover":"http://xxx/_uploads/photos/180202/a653192ecd0ec6f.jpg","mode":"","price":"45.00","product_name":"测试供应商方式","rid":"118150444328","s_color":"","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":0}],"modes":[]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        public List<ColorsBean> colors;
        public List<ItemsBean> items;
        public List<ModesBean> modes;

        public static class ColorsBean implements Parcelable {
            /**
             * name : 深红色
             * valid : true
             */

            public String name;
            public boolean valid;
            public boolean selected;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeByte(this.valid ? (byte) 1 : (byte) 0);
                dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
            }

            public ColorsBean() {
            }

            protected ColorsBean(Parcel in) {
                this.name = in.readString();
                this.valid = in.readByte() != 0;
                this.selected = in.readByte() != 0;
            }

            public static final Creator<ColorsBean> CREATOR = new Creator<ColorsBean>() {
                @Override
                public ColorsBean createFromParcel(Parcel source) {
                    return new ColorsBean(source);
                }

                @Override
                public ColorsBean[] newArray(int size) {
                    return new ColorsBean[size];
                }
            };
        }

        public static class ModesBean implements Parcelable {
            /**
             * name : 规格
             * valid : true
             */

            public String name;
            public boolean valid;
            public boolean selected;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeByte(this.valid ? (byte) 1 : (byte) 0);
                dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
            }

            public ModesBean() {
            }

            protected ModesBean(Parcel in) {
                this.name = in.readString();
                this.valid = in.readByte() != 0;
                this.selected = in.readByte() != 0;
            }

            public static final Creator<ModesBean> CREATOR = new Creator<ModesBean>() {
                @Override
                public ModesBean createFromParcel(Parcel source) {
                    return new ModesBean(source);
                }

                @Override
                public ModesBean[] newArray(int size) {
                    return new ModesBean[size];
                }
            };
        }


        public static class ItemsBean implements Parcelable {
            /**
             * commission_price : 18.52
             * commission_rate : 1.5
             * cover : http://xxx/_uploads/photos/180302/496886303714e7e.jpg
             * mode : 深红色
             * price : 45.00
             * product_name : 测试供应商方式
             * rid : 118130473518
             * s_color : 深红色
             * s_model :
             * s_weight : 0.00
             * sale_price : 0.00
             * stock_count : 8
             */

            public double commission_price;
            public double commission_rate;
            public String cover;
            public String mode;
            public double price;
            public String product_name;
            public String rid;
            public String s_color;
            public String s_model;
            public String s_weight;
            public double sale_price;
            public int stock_count;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeDouble(this.commission_price);
                dest.writeDouble(this.commission_rate);
                dest.writeString(this.cover);
                dest.writeString(this.mode);
                dest.writeDouble(this.price);
                dest.writeString(this.product_name);
                dest.writeString(this.rid);
                dest.writeString(this.s_color);
                dest.writeString(this.s_model);
                dest.writeString(this.s_weight);
                dest.writeDouble(this.sale_price);
                dest.writeInt(this.stock_count);
            }

            public ItemsBean() {
            }

            protected ItemsBean(Parcel in) {
                this.commission_price = in.readDouble();
                this.commission_rate = in.readDouble();
                this.cover = in.readString();
                this.mode = in.readString();
                this.price = in.readDouble();
                this.product_name = in.readString();
                this.rid = in.readString();
                this.s_color = in.readString();
                this.s_model = in.readString();
                this.s_weight = in.readString();
                this.sale_price = in.readDouble();
                this.stock_count = in.readInt();
            }

            public static final Creator<ItemsBean> CREATOR = new Creator<ItemsBean>() {
                @Override
                public ItemsBean createFromParcel(Parcel source) {
                    return new ItemsBean(source);
                }

                @Override
                public ItemsBean[] newArray(int size) {
                    return new ItemsBean[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.colors);
            dest.writeTypedList(this.items);
            dest.writeTypedList(this.modes);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.colors = in.createTypedArrayList(ColorsBean.CREATOR);
            this.items = in.createTypedArrayList(ItemsBean.CREATOR);
            this.modes = in.createTypedArrayList(ModesBean.CREATOR);
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

    public GoodsAllSKUBean() {
    }

    protected GoodsAllSKUBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GoodsAllSKUBean> CREATOR = new Parcelable.Creator<GoodsAllSKUBean>() {
        @Override
        public GoodsAllSKUBean createFromParcel(Parcel source) {
            return new GoodsAllSKUBean(source);
        }

        @Override
        public GoodsAllSKUBean[] newArray(int size) {
            return new GoodsAllSKUBean[size];
        }
    };
}
