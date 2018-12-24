package com.lexivip.lexi.receiveVoucher;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

public class AdapterReceiveVoucherOfficialTransverse extends BaseQuickAdapter<VoucherOfficialBean.DataBean.OfficialCouponsBean,BaseViewHolder> {
    public AdapterReceiveVoucherOfficialTransverse(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoucherOfficialBean.DataBean.OfficialCouponsBean item) {
        helper.setText(R.id.tv_price, item.amount);
        helper.setText(R.id.tv_full,"满"+item.min_amount+"元使用");
        helper.setText(R.id.tv_type,item.category_name);
        helper.setText(R.id.tv_time,"有效期"+DateUtil.getDateByTimestamp(item.created_at,DateUtil.PATTERN_DOT)+
        "至"+DateUtil.getDateByTimestamp(item.end_date,DateUtil.PATTERN_DOT));
        if (item.is_grant){
            helper.setText(R.id.text,"去使用");
            helper.setGone(R.id.iv_null,false);
            helper.setTextColor(R.id.text,Util.getColor(android.R.color.white));
            helper.setTextColor(R.id.textView,Util.getColor(R.color.color_ff6b34));
            helper.setTextColor(R.id.tv_price,Util.getColor(R.color.color_ff6b34));
            helper.setTextColor(R.id.tv_full,Util.getColor(R.color.color_ff6666));
            helper.setTextColor(R.id.tv_time,Util.getColor(R.color.color_d87c7c));
            helper.setTextColor(R.id.tv_type,Util.getColor(R.color.color_ed6f6f));
        }else {
            helper.setText(R.id.text,Util.getString(R.string.text_share_get));
            if (0==item.surplus_count){
                helper.setTextColor(R.id.text,Util.getColor(R.color.color_80fff));
                helper.setGone(R.id.iv_null,true);
                helper.setTextColor(R.id.textView,Util.getColor(R.color.color_29ff6b34));
                helper.setTextColor(R.id.tv_price,Util.getColor(R.color.color_29ff6b34));
                helper.setTextColor(R.id.tv_full,Util.getColor(R.color.color_29ff6b34));
                helper.setTextColor(R.id.tv_time,Util.getColor(R.color.color_29d87c7c));
                helper.setTextColor(R.id.tv_type,Util.getColor(R.color.color_29ed6f6f));
            }else {
                helper.setTextColor(R.id.text,Util.getColor(android.R.color.white));
                helper.setGone(R.id.iv_null,false);
                helper.setTextColor(R.id.textView,Util.getColor(R.color.color_ff6b34));
                helper.setTextColor(R.id.tv_price,Util.getColor(R.color.color_ff6b34));
                helper.setTextColor(R.id.tv_full,Util.getColor(R.color.color_ff6666));
                helper.setTextColor(R.id.tv_time,Util.getColor(R.color.color_d87c7c));
                helper.setTextColor(R.id.tv_type,Util.getColor(R.color.color_ed6f6f));
            }
        }
        helper.addOnClickListener(R.id.recyclerView1);
    }
}
