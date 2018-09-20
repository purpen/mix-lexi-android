package com.thn.lexi.orderList;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.basemodule.tools.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.R;
import com.thn.lexi.order.OrderListBean;

import java.util.List;

public class AdapterOrderListTow extends BaseQuickAdapter<MyOrderListBean.DataBean.OrdersBean.ItemsBean,BaseViewHolder>{
    int status;
    public AdapterOrderListTow(int layoutResId, @Nullable List<MyOrderListBean.DataBean.OrdersBean.ItemsBean> data,int status) {
        super(layoutResId, data);
        this.status=status;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrderListBean.DataBean.OrdersBean.ItemsBean item) {
        GlideUtil.loadImage(item.getStore_logo(),(ImageView) helper.getView(R.id.iv_order_shoping));
        helper.setText(R.id.tv_order_shoping_name, item.getProduct_name());
        helper.setText(R.id.tv_order_shoping_num,"x"+item.getQuantity());
        if (status==2||status==3){
            helper.setVisible(R.id.bt_logistics,true);
        }
        helper.addOnClickListener(R.id.bt_logistics);
    }
}
