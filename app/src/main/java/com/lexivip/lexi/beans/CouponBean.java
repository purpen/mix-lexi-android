package com.lexivip.lexi.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.lexivip.lexi.order.StoreItemBean;

public class CouponBean implements Parcelable {
    /**
     * amount : 10
     * code : UHAOSIWFUVZ
     * count : 100
     * created_at : 1531742069
     * days : 7
     * min_amount : 99
     * products : []
     * reach_amount : 0
     * status : 1
     * type : 1
     * type_text : 全店通用
     * end_date : 1538582400
     * start_date : 1531670400
     */

    public int amount;
    public String code;
    //coupon所在店铺
    public StoreItemBean storeItemBean;
    public int count;
    public int created_at;
    public int days;
    public int min_amount;
    public int reach_amount;
    public int status;
    public int source; //优惠券来源// 1、分享领红包 2、猜图赢现金 3、赠送 4、新人奖励 11、领券中心 12、店铺
    public int type;
    public String type_text;
    public String store_logo;
    public String store_name;
    public String store_rid;
    public long end_date;
    public long start_date;
    public long expired_at;
    public long start_at;
    public long get_at;
    public long end_at;
    public List<ProductBean> products;
    public boolean selected;
    public boolean is_expired;
    public boolean is_used;
    public String category_name;
    public int category_id;
    public boolean is_grant;
    public CouponBean coupon;

    public CouponBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.amount);
        dest.writeString(this.code);
        dest.writeParcelable(this.storeItemBean, flags);
        dest.writeInt(this.count);
        dest.writeInt(this.created_at);
        dest.writeInt(this.days);
        dest.writeInt(this.min_amount);
        dest.writeInt(this.reach_amount);
        dest.writeInt(this.status);
        dest.writeInt(this.source);
        dest.writeInt(this.type);
        dest.writeString(this.type_text);
        dest.writeString(this.store_logo);
        dest.writeString(this.store_name);
        dest.writeString(this.store_rid);
        dest.writeLong(this.end_date);
        dest.writeLong(this.start_date);
        dest.writeLong(this.expired_at);
        dest.writeLong(this.start_at);
        dest.writeLong(this.get_at);
        dest.writeLong(this.end_at);
        dest.writeTypedList(this.products);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_expired ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_used ? (byte) 1 : (byte) 0);
        dest.writeString(this.category_name);
        dest.writeInt(this.category_id);
        dest.writeByte(this.is_grant ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.coupon, flags);
    }

    protected CouponBean(Parcel in) {
        this.amount = in.readInt();
        this.code = in.readString();
        this.storeItemBean = in.readParcelable(StoreItemBean.class.getClassLoader());
        this.count = in.readInt();
        this.created_at = in.readInt();
        this.days = in.readInt();
        this.min_amount = in.readInt();
        this.reach_amount = in.readInt();
        this.status = in.readInt();
        this.source = in.readInt();
        this.type = in.readInt();
        this.type_text = in.readString();
        this.store_logo = in.readString();
        this.store_name = in.readString();
        this.store_rid = in.readString();
        this.end_date = in.readLong();
        this.start_date = in.readLong();
        this.expired_at = in.readLong();
        this.start_at = in.readLong();
        this.get_at = in.readLong();
        this.end_at = in.readLong();
        this.products = in.createTypedArrayList(ProductBean.CREATOR);
        this.selected = in.readByte() != 0;
        this.is_expired = in.readByte() != 0;
        this.is_used = in.readByte() != 0;
        this.category_name = in.readString();
        this.category_id = in.readInt();
        this.is_grant = in.readByte() != 0;
        this.coupon = in.readParcelable(CouponBean.class.getClassLoader());
    }

    public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
        @Override
        public CouponBean createFromParcel(Parcel source) {
            return new CouponBean(source);
        }

        @Override
        public CouponBean[] newArray(int size) {
            return new CouponBean[size];
        }
    };
}
