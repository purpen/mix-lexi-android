package com.lexivip.lexi.cashMoney;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

public class AdapterCashRecord extends BaseQuickAdapter<CashRecordBean.DataBean.RecordListBean,BaseViewHolder> {
    public AdapterCashRecord(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CashRecordBean.DataBean.RecordListBean item) {
        helper.setText(R.id.tv_time,DateUtil.getDateByTimestamp(item.created_at,"yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_put_money,String.valueOf(item.actual_amount));
        // 提现状态 1、成功 2、失败 3、审核中
        switch (item.status){
            case 1:
                helper.setTextColor(R.id.tv_status,Util.getColor(R.color.color_999));
                helper.setText(R.id.tv_status,Util.getString(R.string.text_put_success));
                break;
            case 2:
                helper.setTextColor(R.id.tv_status,Util.getColor(R.color.color_ff6666));
                helper.setText(R.id.tv_status,Util.getString(R.string.text_put_fail));
                break;
            case 3:
                helper.setTextColor(R.id.tv_status,Util.getColor(R.color.color_fb9013));
                helper.setText(R.id.tv_status,Util.getString(R.string.text_examine));
                break;
        }
    }
}
