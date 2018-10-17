package com.lexivip.lexi.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.lexivip.lexi.beans.ProductBean;

import java.util.ArrayList;

public class StoreItemBean implements Parcelable {

    //店铺id
    public String store_rid;

    //计算运费时店铺id字段
    public String rid;

    //是否分销 0、否 1、是
    public String is_distribute;

    public String original_store_rid;

    //买家备注
    public String buyer_remark;

    //商品寄语
    public String blessing_utterance;

    //每个店铺的运费
    public Double expressExpense;

    //店铺优惠券码
    public String coupon_codes;

    //店铺优惠券面值
    public int couponPrice;

    //满减数额
    public double fullReductionAmount;

    //满减描述
    public String fullReductionText;

    //计算运费需要店铺商品列表字段
    public ArrayList<ProductBean> sku_items;

    //店铺商品列表
    public ArrayList<ProductBean> items;

    public StoreItemBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.store_rid);
        dest.writeString(this.rid);
        dest.writeString(this.is_distribute);
        dest.writeString(this.original_store_rid);
        dest.writeString(this.buyer_remark);
        dest.writeString(this.blessing_utterance);
        dest.writeValue(this.expressExpense);
        dest.writeString(this.coupon_codes);
        dest.writeInt(this.couponPrice);
        dest.writeDouble(this.fullReductionAmount);
        dest.writeString(this.fullReductionText);
        dest.writeTypedList(this.sku_items);
        dest.writeTypedList(this.items);
    }

    protected StoreItemBean(Parcel in) {
        this.store_rid = in.readString();
        this.rid = in.readString();
        this.is_distribute = in.readString();
        this.original_store_rid = in.readString();
        this.buyer_remark = in.readString();
        this.blessing_utterance = in.readString();
        this.expressExpense = (Double) in.readValue(Double.class.getClassLoader());
        this.coupon_codes = in.readString();
        this.couponPrice = in.readInt();
        this.fullReductionAmount = in.readDouble();
        this.fullReductionText = in.readString();
        this.sku_items = in.createTypedArrayList(ProductBean.CREATOR);
        this.items = in.createTypedArrayList(ProductBean.CREATOR);
    }

    public static final Creator<StoreItemBean> CREATOR = new Creator<StoreItemBean>() {
        @Override
        public StoreItemBean createFromParcel(Parcel source) {
            return new StoreItemBean(source);
        }

        @Override
        public StoreItemBean[] newArray(int size) {
            return new StoreItemBean[size];
        }
    };
}