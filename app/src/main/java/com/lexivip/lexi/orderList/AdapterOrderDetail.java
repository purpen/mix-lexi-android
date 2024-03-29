package com.lexivip.lexi.orderList;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.ImageSizeConfig;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterOrderDetail extends BaseQuickAdapter<MyOrderListBean.DataBean.OrdersBean.ItemsBean,BaseViewHolder> {
    private int status;
    public AdapterOrderDetail(int layoutResId, @Nullable List<MyOrderListBean.DataBean.OrdersBean.ItemsBean> data,int status) {
        super(layoutResId, data);
        this.status=status;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrderListBean.DataBean.OrdersBean.ItemsBean item) {
        LogUtil.e("显示物流"+status+item.isShow);
        if (item.isShow||1==status||4==status){
            helper.setGone(R.id.relativeLayout,false);
        }else{
            LogUtil.e("显示物流");
            helper.setVisible(R.id.relativeLayout,true);
            helper.setText(R.id.tv_logistics_name,item.getExpress_name());
        }
        GlideUtil.loadImageWithFading(item.getCover()+ImageSizeConfig.SIZE_P30X2,(ImageView) helper.getView(R.id.iv_order_goods));
        helper.setText(R.id.tv_goods_sale_price,"¥"+item.getDeal_price());
        helper.setText(R.id.tv_goods_num,"x"+item.getQuantity());
        TextView price=helper.getView(R.id.tv_goods_price);
        price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.tv_goods_price,"¥"+item.getPrice());
        helper.setText(R.id.tv_order_goods_name,item.getProduct_name());
        StringBuilder stringBuilder=new StringBuilder("");
        if(!item.getS_color().isEmpty())
            stringBuilder.append(item.getS_color()+"/");
        if (!item.getS_model().isEmpty())
            stringBuilder.append(item.getS_model()+"/");
        if (0!=item.getS_weight())
            stringBuilder.append(String.valueOf(item.getS_weight()));
        helper.setText(R.id.tv_goods_parm,stringBuilder);
        helper.addOnClickListener(R.id.bt_logistics);
    }
}
