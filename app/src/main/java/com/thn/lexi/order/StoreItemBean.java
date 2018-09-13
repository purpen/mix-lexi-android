package com.thn.lexi.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.thn.lexi.beans.ProductBean;

import java.util.ArrayList;

public class StoreItemBean implements Parcelable {

    //店铺id
    public String store_rid;


    public String is_distribute;

    public String original_store_rid;

    //买家备注
    public String buyer_remark;

    //商品寄语
    public String blessing_utterance;

    //优惠券码
    public String coupon_codes;

    //店铺商品列表
    public ArrayList<ProductBean> items;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.store_rid);
        dest.writeString(this.is_distribute);
        dest.writeString(this.original_store_rid);
        dest.writeString(this.buyer_remark);
        dest.writeString(this.blessing_utterance);
        dest.writeString(this.coupon_codes);
        dest.writeList(this.items);
    }

    public StoreItemBean() {
    }

    protected StoreItemBean(Parcel in) {
        this.store_rid = in.readString();
        this.is_distribute = in.readString();
        this.original_store_rid = in.readString();
        this.buyer_remark = in.readString();
        this.blessing_utterance = in.readString();
        this.coupon_codes = in.readString();
        this.items = new ArrayList();
        in.readList(this.items, GoodsItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<StoreItemBean> CREATOR = new Parcelable.Creator<StoreItemBean>() {
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
