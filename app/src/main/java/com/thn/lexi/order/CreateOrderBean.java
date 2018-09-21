package com.thn.lexi.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CreateOrderBean implements Parcelable {

    public UserAddressListBean.DataBean consigneeInfo;

    public double orderTotalPrice;

    //收货地址ID
    public String address_rid;

    //
    public String outside_target_id;

    //发票类型
    public String invoice_type;

    //1、发快递 2、自提
    public String ship_mode;

    // 1、小程序；2、H5 3、App 4、TV 5、POS 6、PAD
    public String from_client = "3";

    //推广码
    public String affiliate_code;

    //官方红包码
    public String bonus_code;

    //是否同步返回支付参数 0、否 1、是
    public String sync_pay;

//    上一次浏览的小b店铺rid
    public String last_store_rid;

    //店铺列表
    public ArrayList<StoreItemBean> store_items;

    public CreateOrderBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.consigneeInfo, flags);
        dest.writeDouble(this.orderTotalPrice);
        dest.writeString(this.address_rid);
        dest.writeString(this.outside_target_id);
        dest.writeString(this.invoice_type);
        dest.writeString(this.ship_mode);
        dest.writeString(this.from_client);
        dest.writeString(this.affiliate_code);
        dest.writeString(this.bonus_code);
        dest.writeString(this.sync_pay);
        dest.writeString(this.last_store_rid);
        dest.writeTypedList(this.store_items);
    }

    protected CreateOrderBean(Parcel in) {
        this.consigneeInfo = in.readParcelable(UserAddressListBean.DataBean.class.getClassLoader());
        this.orderTotalPrice = in.readDouble();
        this.address_rid = in.readString();
        this.outside_target_id = in.readString();
        this.invoice_type = in.readString();
        this.ship_mode = in.readString();
        this.from_client = in.readString();
        this.affiliate_code = in.readString();
        this.bonus_code = in.readString();
        this.sync_pay = in.readString();
        this.last_store_rid = in.readString();
        this.store_items = in.createTypedArrayList(StoreItemBean.CREATOR);
    }

    public static final Creator<CreateOrderBean> CREATOR = new Creator<CreateOrderBean>() {
        @Override
        public CreateOrderBean createFromParcel(Parcel source) {
            return new CreateOrderBean(source);
        }

        @Override
        public CreateOrderBean[] newArray(int size) {
            return new CreateOrderBean[size];
        }
    };
}
