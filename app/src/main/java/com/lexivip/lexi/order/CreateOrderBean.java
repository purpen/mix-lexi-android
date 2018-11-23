package com.lexivip.lexi.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CreateOrderBean implements Parcelable {

    public int payWay;

    public String order_rid;

    public UserAddressListBean.DataBean consigneeInfo;

    //订单首单满减价格
    public double firstOrderDiscountPrice;

    //用户选的官方券优惠额度
    public int officialCouponPrice;

    //订单总的满减
    public double fullReductionTotalPrice;

    //官方券是否可选
    public boolean officialCouponCanSelected;

    //用户选店铺券总额度
    public int shopCouponTotalPrice;

    //是否使用官方券
    public boolean notUsingOfficialCoupon;

    //官方券码
    public String officialCouponCode;

    //购物车总价
    public double orderTotalPrice;

    //总的配送费
    public double expressTotalPrice;

    //用户应付总金额
    public double userPayTotalPrice;

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
        dest.writeInt(this.payWay);
        dest.writeString(this.order_rid);
        dest.writeParcelable(this.consigneeInfo, flags);
        dest.writeInt(this.officialCouponPrice);
        dest.writeByte(this.officialCouponCanSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.notUsingOfficialCoupon ? (byte) 1 : (byte) 0);
        dest.writeString(this.officialCouponCode);
        dest.writeDouble(this.orderTotalPrice);
        dest.writeDouble(this.expressTotalPrice);
        dest.writeDouble(this.userPayTotalPrice);
        dest.writeString(this.address_rid);
        dest.writeString(this.outside_target_id);
        dest.writeString(this.invoice_type);
        dest.writeString(this.ship_mode);
        dest.writeString(this.from_client);
        dest.writeString(this.affiliate_code);
        dest.writeString(this.sync_pay);
        dest.writeString(this.last_store_rid);
        dest.writeTypedList(this.store_items);
    }

    protected CreateOrderBean(Parcel in) {
        this.payWay = in.readInt();
        this.order_rid = in.readString();
        this.consigneeInfo = in.readParcelable(UserAddressListBean.DataBean.class.getClassLoader());
        this.officialCouponPrice = in.readInt();
        this.officialCouponCanSelected = in.readByte() != 0;
        this.notUsingOfficialCoupon = in.readByte() != 0;
        this.officialCouponCode = in.readString();
        this.orderTotalPrice = in.readDouble();
        this.expressTotalPrice = in.readDouble();
        this.userPayTotalPrice = in.readDouble();
        this.address_rid = in.readString();
        this.outside_target_id = in.readString();
        this.invoice_type = in.readString();
        this.ship_mode = in.readString();
        this.from_client = in.readString();
        this.affiliate_code = in.readString();
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