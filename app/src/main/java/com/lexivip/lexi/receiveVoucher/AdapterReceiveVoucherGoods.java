package com.lexivip.lexi.receiveVoucher;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterReceiveVoucherGoods extends BaseQuickAdapter<VoucherGoodsBean.DataBean.CouponsBean,BaseViewHolder> {
    public AdapterReceiveVoucherGoods(int layoutResId, @Nullable List<VoucherGoodsBean.DataBean.CouponsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoucherGoodsBean.DataBean.CouponsBean item) {
        helper.setText(R.id.tv_amount,item.amount);
        helper.setText(R.id.tv_full,"满"+item.min_amount+"可用");
        Button button=helper.getView(R.id.button);
        ImageView imageView=helper.getView(R.id.imageView);
        GlideUtil.loadImageWithRadius(item.product_cover,imageView,DimenUtil.dp2px(R.dimen.dp2));
        helper.setText(R.id.tv_name,item.product_name);
        helper.setText(R.id.tv_price,item.product_amount);
        TextView price=helper.getView(R.id.tv_price_old);
        price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        price.setText("¥"+item.product_amount);
        if (0==item.surplus_count){
            helper.setTextColor(R.id.tv_amount,Util.getColor(R.color.color_33ff6666));
            helper.setTextColor(R.id.tv_full,Util.getColor(R.color.color_33ff6666));
            helper.setTextColor(R.id.textView,Util.getColor(R.color.color_33ff6666));
            button.setBackgroundResource(R.drawable.bg_color80fe9167_round);
            button.setTextColor(Util.getColor(android.R.color.white));
            helper.setVisible(R.id.iv_null,true);
        }else {
            helper.setTextColor(R.id.tv_amount,Util.getColor(R.color.color_ff6666));
            helper.setTextColor(R.id.tv_full,Util.getColor(android.R.color.white));
            helper.setTextColor(R.id.textView,Util.getColor(R.color.color_ff6666));
            helper.setVisible(R.id.iv_null,false);
            if (item.is_grant){
                button.setBackgroundResource(R.drawable.bg_colorff6b34_stroke_round);
                button.setTextColor(Util.getColor(R.color.color_ff6b34));
            }else {
                button.setBackgroundResource(R.drawable.bg_colorff6b34_round);
                button.setTextColor(Util.getColor(android.R.color.white));
            }
        }
    }
}
