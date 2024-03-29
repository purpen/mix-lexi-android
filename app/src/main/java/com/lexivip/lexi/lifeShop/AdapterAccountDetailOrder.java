package com.lexivip.lexi.lifeShop;

import android.support.annotation.Nullable;

import com.basemodule.tools.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterAccountDetailOrder extends BaseQuickAdapter<AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderBean,BaseViewHolder> {
    public AdapterAccountDetailOrder(int layoutResId, @Nullable List<AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderBean item) {
        helper.setText(R.id.tv_income,String.valueOf(item.commission_price));
        helper.setText(R.id.tv_order_code,item.orderName);
        helper.setText(R.id.tv_time,DateUtil.getDateByTimestamp(item.created_at,"yyyy-MM-dd  HH:mm:ss"));
        helper.addOnClickListener(R.id.tv_detail);
    }
}
