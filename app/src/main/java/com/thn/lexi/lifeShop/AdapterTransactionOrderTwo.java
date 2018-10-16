package com.thn.lexi.lifeShop;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.basemodule.tools.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.R;

import java.util.List;

public class AdapterTransactionOrderTwo extends BaseQuickAdapter<TransactionOrderBean.DataBean.OrdersBean.ItemsBean,BaseViewHolder> {
    public AdapterTransactionOrderTwo(int layoutResId, @Nullable List<TransactionOrderBean.DataBean.OrdersBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionOrderBean.DataBean.OrdersBean.ItemsBean item) {
        ImageView imageView=helper.getView(R.id.iv_shop);
        GlideUtil.loadImageWithFading(item.store_logo,imageView);
        helper.setText(R.id.tv_goods_name,item.store_name);
        StringBuilder stringBuilder=new StringBuilder("");
        if(!item.s_color.isEmpty())
            stringBuilder.append(item.s_color+"/");
        if (!item.s_model.isEmpty())
            stringBuilder.append(item.s_model+"/");
        if (0!=item.s_weight)
            stringBuilder.append(String.valueOf(item.s_weight));
        helper.setText(R.id.tv_goods_parm,stringBuilder);
        helper.setText(R.id.tv_goods_num,"x"+item.quantity);
        helper.setText(R.id.tv_goods,"¥"+item.deal_price);
    }
}
