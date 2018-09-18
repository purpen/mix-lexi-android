package com.thn.lexi.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductBean implements Parcelable {

    /**
     * category_id : 分类id
     * commission_price : 分销佣金
     * commission_rate : 佣金比率
     * cover : 封面图片链接地址
     * cover_id : 封面图id
     * custom_details : 定制详情
     * delivery_country : 发货地名
     * delivery_country_id : 发货地编号
     * features : 商品推荐语
     * have_distributed : 店铺是否分销过该商品
     * id_code : 商品简码
     * is_custom_made : 是否接单定制
     * is_custom_service : 是否支持定制化服务
     * is_distributed : 是否分销
     * is_free_postage : 是否包邮
     * is_like : 用户是否添加喜欢
     * is_made_holiday : 制作周期是否包含节假日
     * is_sold_out : 是否已售罄
     * is_proprietary : 是否自营商品
     * like_count : 商品被喜欢数
     * is_wish : 用户是否加入心愿清单
     * made_cycle : 制作周期,天数
     * max_price : 最大售价
     * max_sale_price : 最大促销价
     * min_price : 最小售价
     * min_sale_price : 最小促销价
     * modes : ["商品型号1","商品型号2"]
     * name : 商品名
     * published_at : 商品发布时间
     * real_price : 佣金对应的商品原价
     * real_sale_price : 佣金对应的商品折扣价
     * rid : 商品编号
     * second_category_id : 二级分类id
     * status : 商品状态: 0=仓库中, 1=出售中, 2=下架中
     * sticked : 是否推荐
     * store_name : 店铺名
     * store_rid : 店铺编号
     * top_category_id : 一级分类id
     * total_stock : 库存数
     */

    public String category_id;
    public String commission_price;
    public String commission_rate;
    public String cover;
    public String cover_id;
    public String custom_details;
    public String delivery_country;
    public String delivery_province;
    public String delivery_city;
    public String delivery_country_id;
    public String features;
    public String have_distributed;
    public String id_code;
    public String s_color;
    public String s_model;
    public String product_name;
    public int quantity;
    public String sku;
    public String product_rid;
    public int stock_quantity;
    public boolean is_custom_made;
    public boolean is_custom_service;
    public boolean is_distributed;
    public boolean is_free_postage;
    public boolean is_like;
    public boolean is_made_holiday;
    public boolean is_sold_out;
    public boolean is_proprietary;
    public String like_count;
    public boolean is_wish;
    public String made_cycle;
    public String max_price;
    public String max_sale_price;
    public double sale_price;
    public double price;
    public String min_price;
    public String min_sale_price;
    public String name;
    public String stick_text;
    public String published_at;
    public double real_price;
    public double real_sale_price;
    public String rid;
    public String second_category_id;
    public String status;
    public String sticked;
    public String store_name;
    public String store_rid;
    public String top_category_id;
    public String total_stock;
    public List<String> modes;
    public List<UserBean> product_like_users;

    public ProductBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category_id);
        dest.writeString(this.commission_price);
        dest.writeString(this.commission_rate);
        dest.writeString(this.cover);
        dest.writeString(this.cover_id);
        dest.writeString(this.custom_details);
        dest.writeString(this.delivery_country);
        dest.writeString(this.delivery_province);
        dest.writeString(this.delivery_city);
        dest.writeString(this.delivery_country_id);
        dest.writeString(this.features);
        dest.writeString(this.have_distributed);
        dest.writeString(this.id_code);
        dest.writeString(this.s_color);
        dest.writeString(this.s_model);
        dest.writeString(this.product_name);
        dest.writeInt(this.quantity);
        dest.writeString(this.sku);
        dest.writeString(this.product_rid);
        dest.writeInt(this.stock_quantity);
        dest.writeByte(this.is_custom_made ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_custom_service ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_distributed ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_free_postage ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_like ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_made_holiday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_sold_out ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_proprietary ? (byte) 1 : (byte) 0);
        dest.writeString(this.like_count);
        dest.writeByte(this.is_wish ? (byte) 1 : (byte) 0);
        dest.writeString(this.made_cycle);
        dest.writeString(this.max_price);
        dest.writeString(this.max_sale_price);
        dest.writeDouble(this.sale_price);
        dest.writeDouble(this.price);
        dest.writeString(this.min_price);
        dest.writeString(this.min_sale_price);
        dest.writeString(this.name);
        dest.writeString(this.stick_text);
        dest.writeString(this.published_at);
        dest.writeDouble(this.real_price);
        dest.writeDouble(this.real_sale_price);
        dest.writeString(this.rid);
        dest.writeString(this.second_category_id);
        dest.writeString(this.status);
        dest.writeString(this.sticked);
        dest.writeString(this.store_name);
        dest.writeString(this.store_rid);
        dest.writeString(this.top_category_id);
        dest.writeString(this.total_stock);
        dest.writeStringList(this.modes);
        dest.writeTypedList(this.product_like_users);
    }

    protected ProductBean(Parcel in) {
        this.category_id = in.readString();
        this.commission_price = in.readString();
        this.commission_rate = in.readString();
        this.cover = in.readString();
        this.cover_id = in.readString();
        this.custom_details = in.readString();
        this.delivery_country = in.readString();
        this.delivery_province = in.readString();
        this.delivery_city = in.readString();
        this.delivery_country_id = in.readString();
        this.features = in.readString();
        this.have_distributed = in.readString();
        this.id_code = in.readString();
        this.s_color = in.readString();
        this.s_model = in.readString();
        this.product_name = in.readString();
        this.quantity = in.readInt();
        this.sku = in.readString();
        this.product_rid = in.readString();
        this.stock_quantity = in.readInt();
        this.is_custom_made = in.readByte() != 0;
        this.is_custom_service = in.readByte() != 0;
        this.is_distributed = in.readByte() != 0;
        this.is_free_postage = in.readByte() != 0;
        this.is_like = in.readByte() != 0;
        this.is_made_holiday = in.readByte() != 0;
        this.is_sold_out = in.readByte() != 0;
        this.is_proprietary = in.readByte() != 0;
        this.like_count = in.readString();
        this.is_wish = in.readByte() != 0;
        this.made_cycle = in.readString();
        this.max_price = in.readString();
        this.max_sale_price = in.readString();
        this.sale_price = in.readDouble();
        this.price = in.readDouble();
        this.min_price = in.readString();
        this.min_sale_price = in.readString();
        this.name = in.readString();
        this.stick_text = in.readString();
        this.published_at = in.readString();
        this.real_price = in.readDouble();
        this.real_sale_price = in.readDouble();
        this.rid = in.readString();
        this.second_category_id = in.readString();
        this.status = in.readString();
        this.sticked = in.readString();
        this.store_name = in.readString();
        this.store_rid = in.readString();
        this.top_category_id = in.readString();
        this.total_stock = in.readString();
        this.modes = in.createStringArrayList();
        this.product_like_users = in.createTypedArrayList(UserBean.CREATOR);
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel source) {
            return new ProductBean(source);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };
}
