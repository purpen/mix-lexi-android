package com.thn.lexi.lifeShop;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.R;

import java.util.List;

public class AdapterTransactionRecord extends BaseQuickAdapter<TransactionRecordBean.DataBean.TransactionsBean,BaseViewHolder> {
    public AdapterTransactionRecord(int layoutResId, @Nullable List<TransactionRecordBean.DataBean.TransactionsBean> data, FragmentActivity activity) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionRecordBean.DataBean.TransactionsBean item) {
        helper.setText(R.id.tv_order_code,item.order_rid);
        helper.setText(R.id.tv_order_amount,String.valueOf(item.actual_amount));
        helper.setText(R.id.tv_order_time,DateUtil.getDateByTimestamp(item.payed_at, "yyyy-MM-dd HH:mm:ss"));
        switch (item.status){
            case 1:
                helper.setTextColor(R.id.tv_order_status,Util.getColor(R.color.color_fb9013));
                helper.setText(R.id.tv_order_status,"待结算");
                break;
            case 2:
                helper.setTextColor(R.id.tv_order_status,Util.getColor(R.color.color_6ed7af));
                helper.setText(R.id.tv_order_status,"成功");
                break;
            case 3:
                helper.setTextColor(R.id.tv_order_status,Util.getColor(R.color.color_b2b2b2));
                helper.setText(R.id.tv_order_status,"已退款");
                break;
        }
    }
}
