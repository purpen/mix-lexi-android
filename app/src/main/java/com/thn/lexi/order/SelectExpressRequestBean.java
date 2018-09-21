package com.thn.lexi.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.thn.lexi.beans.ProductBean;

import java.util.List;

public class SelectExpressRequestBean implements Parcelable {

    //显示请选择的SKU
    public ProductBean productBean;

    //默认快递
    public ExpressInfoBean defaultExpress;

    //收货地址
    public String expressSendAddress;

    //收货地址id
    public String address_rid;
    //运费模板
    public String fid;

    //商品
    public List<ProductBean> items;

    public SelectExpressRequestBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.productBean, flags);
        dest.writeParcelable(this.defaultExpress, flags);
        dest.writeString(this.expressSendAddress);
        dest.writeString(this.address_rid);
        dest.writeString(this.fid);
        dest.writeTypedList(this.items);
    }

    protected SelectExpressRequestBean(Parcel in) {
        this.productBean = in.readParcelable(ProductBean.class.getClassLoader());
        this.defaultExpress = in.readParcelable(ExpressInfoBean.class.getClassLoader());
        this.expressSendAddress = in.readString();
        this.address_rid = in.readString();
        this.fid = in.readString();
        this.items = in.createTypedArrayList(ProductBean.CREATOR);
    }

    public static final Creator<SelectExpressRequestBean> CREATOR = new Creator<SelectExpressRequestBean>() {
        @Override
        public SelectExpressRequestBean createFromParcel(Parcel source) {
            return new SelectExpressRequestBean(source);
        }

        @Override
        public SelectExpressRequestBean[] newArray(int size) {
            return new SelectExpressRequestBean[size];
        }
    };
}
