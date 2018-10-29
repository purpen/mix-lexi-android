package com.lexivip.lexi.receiveVoucher;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterReceiveVoucherBrandTwo extends BaseQuickAdapter<VoucherBrandBean.DataBean.CouponsBean.ProductSkuBean,BaseViewHolder> {
    public AdapterReceiveVoucherBrandTwo(int layoutResId, @Nullable List<VoucherBrandBean.DataBean.CouponsBean.ProductSkuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoucherBrandBean.DataBean.CouponsBean.ProductSkuBean item) {
        ImageView imageView=helper.getView(R.id.imageView);
        GlideUtil.loadImageWithRadius(item.product_cover,imageView,DimenUtil.dp2px(R.dimen.dp2));
        helper.setText(R.id.tv_name,item.product_name);
        helper.setText(R.id.tv_price,item.product_coupon_amount);
        TextView textView=helper.getView(R.id.tv_price_old);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        textView.setText(item.product_amount);
    }
}
