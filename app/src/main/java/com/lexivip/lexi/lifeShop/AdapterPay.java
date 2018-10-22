package com.lexivip.lexi.lifeShop;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterPay extends BaseQuickAdapter<PayBean.DataBean.OrderListBean,BaseViewHolder> {
    public AdapterPay(int layoutResId, @Nullable List<PayBean.DataBean.OrderListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayBean.DataBean.OrderListBean item) {
        helper.setText(R.id.tv_name,item.store_name);
        helper.setText(R.id.tv_price,"￥"+item.user_pay_amount);
        helper.setText(R.id.tv_num,"共"+item.total_quantity+"件");
    }
}
