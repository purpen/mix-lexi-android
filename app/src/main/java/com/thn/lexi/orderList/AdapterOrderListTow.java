package com.thn.lexi.orderList;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.order.OrderListBean;

import java.util.List;

public class AdapterOrderListTow extends BaseQuickAdapter<OrderListBean.DataBean.OrdersBean.ItemsBean,BaseViewHolder>{
    int status;
    public AdapterOrderListTow(int layoutResId, @Nullable List<OrderListBean.DataBean.OrdersBean.ItemsBean> data,int status) {
        super(layoutResId, data);
        this.status=status;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.DataBean.OrdersBean.ItemsBean item) {

    }
}
