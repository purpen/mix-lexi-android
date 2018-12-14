package com.lexivip.lexi.cashMoney;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterCashMoney extends BaseQuickAdapter<CashItemBean,BaseViewHolder> {
    public AdapterCashMoney(int layoutResId, @Nullable List<CashItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CashItemBean item) {
        helper.setText(R.id.text,item.name);
        if (getLoadMoreViewPosition()==0){
            helper.setVisible(R.id.iv_title,true);
        }else {
            helper.setVisible(R.id.iv_title,false);
        }
        LinearLayout linearLayout=helper.getView(R.id.linearLayout);
        if (item.isSelect){
            linearLayout.setBackgroundResource(R.drawable.bg_color5fe4b1_line_radiu4);
        }else {
            linearLayout.setBackgroundResource(R.drawable.bg_colorccc_line_radiu4);
        }
    }
}
