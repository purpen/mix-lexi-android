package com.lexivip.lexi.receiveVoucher;

import android.support.annotation.Nullable;

import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterReceiveVoucherOfficial extends BaseQuickAdapter<VoucherOfficialBean.DataBean.OfficialCouponsBean,BaseViewHolder> {
    public AdapterReceiveVoucherOfficial(int layoutResId, @Nullable List<VoucherOfficialBean.DataBean.OfficialCouponsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoucherOfficialBean.DataBean.OfficialCouponsBean item) {
        helper.setText(R.id.tv_price, item.amount);
        helper.setText(R.id.tv_full,"满"+item.min_amount+"元使用");
        if (item.is_grant){
            helper.setBackgroundRes(R.id.button,R.drawable.border_image_round);
            helper.setTextColor(R.id.button,Util.getColor(android.R.color.white));
            helper.setText(R.id.button,Util.getString(R.string.text_go_use));
            helper.setGone(R.id.iv_null_bg,false);
            helper.setGone(R.id.iv_null,false);
        }else {
            helper.setBackgroundRes(R.id.button,R.drawable.bg_radius_round_white);
            helper.setTextColor(R.id.button,Util.getColor(R.color.color_fd7162));
            helper.setText(R.id.button,Util.getString(R.string.text_immediately_receive));
            helper.setGone(R.id.iv_null_bg,false);
            helper.setGone(R.id.iv_null,false);
            if (0==item.surplus_count){
                helper.setGone(R.id.iv_null_bg,true);
                helper.setGone(R.id.iv_null,true);
            }else {
                helper.setGone(R.id.iv_null_bg,false);
                helper.setGone(R.id.iv_null,false);
            }
        }
        helper.addOnClickListener(R.id.button);
    }
}
