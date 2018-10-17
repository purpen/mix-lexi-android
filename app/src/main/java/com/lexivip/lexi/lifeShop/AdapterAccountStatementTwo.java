package com.lexivip.lexi.lifeShop;

import android.support.annotation.Nullable;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterAccountStatementTwo extends BaseQuickAdapter<AccountStatementBean.DataBean.StatementsBeanX.StatementsBean,BaseViewHolder> {
    public AdapterAccountStatementTwo(int layoutResId, @Nullable List<AccountStatementBean.DataBean.StatementsBeanX.StatementsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountStatementBean.DataBean.StatementsBeanX.StatementsBean item) {
        helper.setText(R.id.tv_time,DateUtil.getDateByTimestamp(item.created_at,"yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_put_money,String.valueOf(item.actual_amount));
        // 提现状态 1、审核中 2、成功 3、失败
        switch (item.status){
            case 1:
                helper.setTextColor(R.id.tv_status,Util.getColor(R.color.color_fb9013));
                helper.setText(R.id.tv_status,Util.getString(R.string.text_examine));
                break;
            case 2:
                helper.setTextColor(R.id.tv_status,Util.getColor(R.color.color_6ed7af));
                helper.setText(R.id.tv_status,Util.getString(R.string.text_put_success));
                break;
            case 3:
                helper.setTextColor(R.id.tv_status,Util.getColor(R.color.color_ff6666));
                helper.setText(R.id.tv_status,Util.getString(R.string.text_put_fail));
                break;
        }
    }
}
