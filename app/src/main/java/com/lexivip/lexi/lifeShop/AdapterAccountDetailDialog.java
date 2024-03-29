package com.lexivip.lexi.lifeShop;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.basemodule.tools.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.ImageSizeConfig;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterAccountDetailDialog extends BaseQuickAdapter<AccountDetailOrderBean.DataBean.ItemsBean,BaseViewHolder> {
    public AdapterAccountDetailDialog(int layoutResId, @Nullable List<AccountDetailOrderBean.DataBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountDetailOrderBean.DataBean.ItemsBean item) {
        ImageView imageView=helper.getView(R.id.iv_shop);
        GlideUtil.loadImageWithFading(item.cover+ImageSizeConfig.SIZE_P30X2,imageView);
        helper.setText(R.id.tv_goods_name,item.product_name);
        helper.setText(R.id.tv_goods_num,"x"+item.quantity);
        helper.setText(R.id.tv_goods,"¥"+item.deal_price);
        StringBuilder stringBuilder=new StringBuilder("");
        if(!TextUtils.isEmpty(item.s_color))
            stringBuilder.append(item.s_color+"/");
        if (!TextUtils.isEmpty(item.s_model))
            stringBuilder.append(item.s_model+"/");
        if (!TextUtils.isEmpty(item.s_weight))
            stringBuilder.append(String.valueOf(item.s_weight));
        helper.setText(R.id.tv_goods_parm,stringBuilder);
        helper.setText(R.id.tv_order_income,String.valueOf(item.order_sku_commission_price*item.quantity));
    }
}
