package com.lexivip.lexi.order;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodsItem implements Parcelable {
    //sku id
    public String rid;

    //商品数量
    public String quantity;

    //物流公司ID
    public String express_id;

    //发货仓库ID
    public String warehouse_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rid);
        dest.writeString(this.quantity);
        dest.writeString(this.express_id);
        dest.writeString(this.warehouse_id);
    }

    public GoodsItem() {
    }

    protected GoodsItem(Parcel in) {
        this.rid = in.readString();
        this.quantity = in.readString();
        this.express_id = in.readString();
        this.warehouse_id = in.readString();
    }

    public static final Parcelable.Creator<GoodsItem> CREATOR = new Parcelable.Creator<GoodsItem>() {
        @Override
        public GoodsItem createFromParcel(Parcel source) {
            return new GoodsItem(source);
        }

        @Override
        public GoodsItem[] newArray(int size) {
            return new GoodsItem[size];
        }
    };
}
