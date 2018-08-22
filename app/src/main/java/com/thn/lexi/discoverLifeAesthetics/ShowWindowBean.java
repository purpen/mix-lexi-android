package com.thn.lexi.discoverLifeAesthetics;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ShowWindowBean implements Parcelable {

    /**
     * data : {"count":8,"next":false,"prev":false,"shop_windows":[{"comment_count":"评论数","description":"橱窗详情","is_follow":"是否关注过该橱窗","is_official":"是否官方橱窗","keywords":["橱窗关键词一","橱窗关键词二"],"like_count":"橱窗喜欢数","product_count":5,"product_covers":["http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg"],"products":[{"category_id":18,"commission_price":846.89,"commission_rate":24.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180710/fbba50f0da91a7a.jpg","cover_id":10,"custom_details":"可以刻名字,生辰八字","delivery_country":"","delivery_country_id":null,"features":"获得更多的成长值，加速商铺成长获取更多特权：发布三星级及以上的产品可以获得与星级数量等额的成长值，每日上限为20点；","have_distributed":true,"id_code":"","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":false,"is_sold_out":false,"like_count":1,"made_cycle":5,"material_id":4,"material_name":"毛线","max_price":5456.7,"max_sale_price":0,"min_price":3234.5,"min_sale_price":2245,"modes":["3*1 白色","3*2 绿色","3*3 紫色"],"name":"商品名","published_at":1531213666,"real_price":5456.7,"real_sale_price":0,"rid":"8479032186","second_category_id":17,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"店铺编号","style_id":"商品风格编号","style_name":"商品风格名","top_category_id":1,"total_stock":45}],"rid":"橱窗编号","title":"橱窗标题","user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"用户名"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * count : 8
         * next : false
         * prev : false
         * shop_windows : [{"comment_count":"评论数","description":"橱窗详情","is_follow":"是否关注过该橱窗","is_official":"是否官方橱窗","keywords":["橱窗关键词一","橱窗关键词二"],"like_count":"橱窗喜欢数","product_count":5,"product_covers":["http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg"],"products":[{"category_id":18,"commission_price":846.89,"commission_rate":24.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180710/fbba50f0da91a7a.jpg","cover_id":10,"custom_details":"可以刻名字,生辰八字","delivery_country":"","delivery_country_id":null,"features":"获得更多的成长值，加速商铺成长获取更多特权：发布三星级及以上的产品可以获得与星级数量等额的成长值，每日上限为20点；","have_distributed":true,"id_code":"","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":false,"is_sold_out":false,"like_count":1,"made_cycle":5,"material_id":4,"material_name":"毛线","max_price":5456.7,"max_sale_price":0,"min_price":3234.5,"min_sale_price":2245,"modes":["3*1 白色","3*2 绿色","3*3 紫色"],"name":"商品名","published_at":1531213666,"real_price":5456.7,"real_sale_price":0,"rid":"8479032186","second_category_id":17,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"店铺编号","style_id":"商品风格编号","style_name":"商品风格名","top_category_id":1,"total_stock":45}],"rid":"橱窗编号","title":"橱窗标题","user_avatar":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","user_name":"用户名"}]
         */

        public int count;
        public List<ShopWindowsBean> shop_windows;

        public static class ShopWindowsBean implements Parcelable {
            /**
             * comment_count : 评论数
             * description : 橱窗详情
             * is_follow : 是否关注过该橱窗
             * is_official : 是否官方橱窗
             * keywords : ["橱窗关键词一","橱窗关键词二"]
             * like_count : 橱窗喜欢数
             * product_count : 5
             * product_covers : ["http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180718/f1a30ad8b52107c.gif","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg"]
             * products : [{"category_id":18,"commission_price":846.89,"commission_rate":24.5,"cover":"http://127.0.0.1:9000/_uploads/photos/180710/fbba50f0da91a7a.jpg","cover_id":10,"custom_details":"可以刻名字,生辰八字","delivery_country":"","delivery_country_id":null,"features":"获得更多的成长值，加速商铺成长获取更多特权：发布三星级及以上的产品可以获得与星级数量等额的成长值，每日上限为20点；","have_distributed":true,"id_code":"","is_custom_made":true,"is_custom_service":true,"is_distributed":true,"is_free_postage":false,"is_made_holiday":false,"is_proprietary":false,"is_sold_out":false,"like_count":1,"made_cycle":5,"material_id":4,"material_name":"毛线","max_price":5456.7,"max_sale_price":0,"min_price":3234.5,"min_sale_price":2245,"modes":["3*1 白色","3*2 绿色","3*3 紫色"],"name":"商品名","published_at":1531213666,"real_price":5456.7,"real_sale_price":0,"rid":"8479032186","second_category_id":17,"status":1,"sticked":false,"store_name":"店铺名","store_rid":"店铺编号","style_id":"商品风格编号","style_name":"商品风格名","top_category_id":1,"total_stock":45}]
             * rid : 橱窗编号
             * title : 橱窗标题
             * user_avatar : http://kg.erp.taihuoniao.com/static/img/default-logo.png
             * user_name : 用户名
             */

            public int comment_count;
            public String description;
            public boolean is_follow;
            public boolean is_official;
            public int like_count;
            public boolean is_like;
            public int product_count;
            public String rid;
            public String title;
            public String user_avatar;
            public String user_name;
            public String uid;
            public List<String> keywords;
            public List<String> product_covers;
            public List<ProductsBean> products;

            public static class ProductsBean implements Parcelable {
                /**
                 * category_id : 18
                 * commission_price : 846.89
                 * commission_rate : 24.5
                 * cover : http://127.0.0.1:9000/_uploads/photos/180710/fbba50f0da91a7a.jpg
                 * cover_id : 10
                 * custom_details : 可以刻名字,生辰八字
                 * delivery_country :
                 * delivery_country_id : null
                 * features : 获得更多的成长值，加速商铺成长获取更多特权：发布三星级及以上的产品可以获得与星级数量等额的成长值，每日上限为20点；
                 * have_distributed : true
                 * id_code :
                 * is_custom_made : true
                 * is_custom_service : true
                 * is_distributed : true
                 * is_free_postage : false
                 * is_made_holiday : false
                 * is_proprietary : false
                 * is_sold_out : false
                 * like_count : 1
                 * made_cycle : 5
                 * material_id : 4
                 * material_name : 毛线
                 * max_price : 5456.7
                 * max_sale_price : 0
                 * min_price : 3234.5
                 * min_sale_price : 2245
                 * modes : ["3*1 白色","3*2 绿色","3*3 紫色"]
                 * name : 商品名
                 * published_at : 1531213666
                 * real_price : 5456.7
                 * real_sale_price : 0
                 * rid : 8479032186
                 * second_category_id : 17
                 * status : 1
                 * sticked : false
                 * store_name : 店铺名
                 * store_rid : 店铺编号
                 * style_id : 商品风格编号
                 * style_name : 商品风格名
                 * top_category_id : 1
                 * total_stock : 45
                 */

                public int category_id;
                public double commission_price;
                public double commission_rate;
                public String cover;
                public int cover_id;
                public String custom_details;
                public String delivery_country;
                public String features;
                public boolean have_distributed;
                public String id_code;
                public boolean is_custom_made;
                public boolean is_custom_service;
                public boolean is_distributed;
                public boolean is_free_postage;
                public boolean is_made_holiday;
                public boolean is_proprietary;
                public boolean is_sold_out;
                public int like_count;
                public int made_cycle;
                public int material_id;
                public String material_name;
                public double max_price;
                public int max_sale_price;
                public double min_price;
                public int min_sale_price;
                public String name;
                public int published_at;
                public double real_price;
                public int real_sale_price;
                public String rid;
                public int second_category_id;
                public int status;
                public boolean sticked;
                public String store_name;
                public String store_rid;
                public String style_id;
                public String style_name;
                public int top_category_id;
                public int total_stock;
                public List<String> modes;

                public ProductsBean() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.category_id);
                    dest.writeDouble(this.commission_price);
                    dest.writeDouble(this.commission_rate);
                    dest.writeString(this.cover);
                    dest.writeInt(this.cover_id);
                    dest.writeString(this.custom_details);
                    dest.writeString(this.delivery_country);
                    dest.writeString(this.features);
                    dest.writeByte(this.have_distributed ? (byte) 1 : (byte) 0);
                    dest.writeString(this.id_code);
                    dest.writeByte(this.is_custom_made ? (byte) 1 : (byte) 0);
                    dest.writeByte(this.is_custom_service ? (byte) 1 : (byte) 0);
                    dest.writeByte(this.is_distributed ? (byte) 1 : (byte) 0);
                    dest.writeByte(this.is_free_postage ? (byte) 1 : (byte) 0);
                    dest.writeByte(this.is_made_holiday ? (byte) 1 : (byte) 0);
                    dest.writeByte(this.is_proprietary ? (byte) 1 : (byte) 0);
                    dest.writeByte(this.is_sold_out ? (byte) 1 : (byte) 0);
                    dest.writeInt(this.like_count);
                    dest.writeInt(this.made_cycle);
                    dest.writeInt(this.material_id);
                    dest.writeString(this.material_name);
                    dest.writeDouble(this.max_price);
                    dest.writeInt(this.max_sale_price);
                    dest.writeDouble(this.min_price);
                    dest.writeInt(this.min_sale_price);
                    dest.writeString(this.name);
                    dest.writeInt(this.published_at);
                    dest.writeDouble(this.real_price);
                    dest.writeInt(this.real_sale_price);
                    dest.writeString(this.rid);
                    dest.writeInt(this.second_category_id);
                    dest.writeInt(this.status);
                    dest.writeByte(this.sticked ? (byte) 1 : (byte) 0);
                    dest.writeString(this.store_name);
                    dest.writeString(this.store_rid);
                    dest.writeString(this.style_id);
                    dest.writeString(this.style_name);
                    dest.writeInt(this.top_category_id);
                    dest.writeInt(this.total_stock);
                    dest.writeStringList(this.modes);
                }

                protected ProductsBean(Parcel in) {
                    this.category_id = in.readInt();
                    this.commission_price = in.readDouble();
                    this.commission_rate = in.readDouble();
                    this.cover = in.readString();
                    this.cover_id = in.readInt();
                    this.custom_details = in.readString();
                    this.delivery_country = in.readString();
                    this.features = in.readString();
                    this.have_distributed = in.readByte() != 0;
                    this.id_code = in.readString();
                    this.is_custom_made = in.readByte() != 0;
                    this.is_custom_service = in.readByte() != 0;
                    this.is_distributed = in.readByte() != 0;
                    this.is_free_postage = in.readByte() != 0;
                    this.is_made_holiday = in.readByte() != 0;
                    this.is_proprietary = in.readByte() != 0;
                    this.is_sold_out = in.readByte() != 0;
                    this.like_count = in.readInt();
                    this.made_cycle = in.readInt();
                    this.material_id = in.readInt();
                    this.material_name = in.readString();
                    this.max_price = in.readDouble();
                    this.max_sale_price = in.readInt();
                    this.min_price = in.readDouble();
                    this.min_sale_price = in.readInt();
                    this.name = in.readString();
                    this.published_at = in.readInt();
                    this.real_price = in.readDouble();
                    this.real_sale_price = in.readInt();
                    this.rid = in.readString();
                    this.second_category_id = in.readInt();
                    this.status = in.readInt();
                    this.sticked = in.readByte() != 0;
                    this.store_name = in.readString();
                    this.store_rid = in.readString();
                    this.style_id = in.readString();
                    this.style_name = in.readString();
                    this.top_category_id = in.readInt();
                    this.total_stock = in.readInt();
                    this.modes = in.createStringArrayList();
                }

                public static final Creator<ProductsBean> CREATOR = new Creator<ProductsBean>() {
                    @Override
                    public ProductsBean createFromParcel(Parcel source) {
                        return new ProductsBean(source);
                    }

                    @Override
                    public ProductsBean[] newArray(int size) {
                        return new ProductsBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.comment_count);
                dest.writeString(this.description);
                dest.writeByte(this.is_follow ? (byte) 1 : (byte) 0);
                dest.writeByte(this.is_official ? (byte) 1 : (byte) 0);
                dest.writeInt(this.like_count);
                dest.writeByte(this.is_like ? (byte) 1 : (byte) 0);
                dest.writeInt(this.product_count);
                dest.writeString(this.rid);
                dest.writeString(this.title);
                dest.writeString(this.user_avatar);
                dest.writeString(this.user_name);
                dest.writeStringList(this.keywords);
                dest.writeStringList(this.product_covers);
                dest.writeList(this.products);
            }

            public ShopWindowsBean() {
            }

            protected ShopWindowsBean(Parcel in) {
                this.comment_count = in.readInt();
                this.description = in.readString();
                this.is_follow = in.readByte() != 0;
                this.is_official = in.readByte() != 0;
                this.like_count = in.readInt();
                this.is_like = in.readByte() != 0;
                this.product_count = in.readInt();
                this.rid = in.readString();
                this.title = in.readString();
                this.user_avatar = in.readString();
                this.user_name = in.readString();
                this.keywords = in.createStringArrayList();
                this.product_covers = in.createStringArrayList();
                this.products = new ArrayList<ProductsBean>();
                in.readList(this.products, ProductsBean.class.getClassLoader());
            }

            public static final Creator<ShopWindowsBean> CREATOR = new Creator<ShopWindowsBean>() {
                @Override
                public ShopWindowsBean createFromParcel(Parcel source) {
                    return new ShopWindowsBean(source);
                }

                @Override
                public ShopWindowsBean[] newArray(int size) {
                    return new ShopWindowsBean[size];
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
            dest.writeList(this.shop_windows);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.shop_windows = new ArrayList<ShopWindowsBean>();
            in.readList(this.shop_windows, ShopWindowsBean.class.getClassLoader());
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

    public ShowWindowBean() {
    }

    protected ShowWindowBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ShowWindowBean> CREATOR = new Parcelable.Creator<ShowWindowBean>() {
        @Override
        public ShowWindowBean createFromParcel(Parcel source) {
            return new ShowWindowBean(source);
        }

        @Override
        public ShowWindowBean[] newArray(int size) {
            return new ShowWindowBean[size];
        }
    };
}
