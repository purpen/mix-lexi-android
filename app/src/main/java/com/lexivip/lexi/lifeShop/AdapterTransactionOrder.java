package com.lexivip.lexi.lifeShop;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.CustomLinearLayoutManager;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterTransactionOrder extends BaseQuickAdapter<TransactionOrderBean.DataBean.OrdersBean,BaseViewHolder> {
    private Activity activity;

    public AdapterTransactionOrder(int layoutResId, @Nullable List<TransactionOrderBean.DataBean.OrdersBean> data, Activity activity) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionOrderBean.DataBean.OrdersBean item) {
        ImageView imageView=helper.getView(R.id.iv_logo);
        GlideUtil.loadImageWithRadius(item.user_info.user_logo,imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp4));
        helper.setText(R.id.tv_name,item.store.store_name);
        helper.setText(R.id.tv_order_total,"¥"+String.valueOf(item.pay_amount));
        helper.setText(R.id.tv_order_code,item.rid);
        helper.setText(R.id.tv_order_time,DateUtil.getDateByTimestamp(item.received_at,"yyyy年MM月dd日 HH:mm:ss"));
        double commission=0;
        for (int i=0;i<item.items.size();i++){
            commission=commission+(item.items.get(i).order_sku_commission_price*item.items.get(i).quantity);
        }
        helper.setText(R.id.tv_order_income,String.valueOf(commission));
        RecyclerView recyclerView=helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(activity));
        AdapterTransactionOrderTwo adapter=new AdapterTransactionOrderTwo(R.layout.adapter_transaction_order_two,item.items);
        recyclerView.setAdapter(adapter);
        // 生活馆订单状态， 1、待发货 2、已发货 3、已完成
        switch (item.life_order_status){
            case 1:
                helper.setTextColor(R.id.tv_already_delivery,Util.getColor(R.color.color_999));
                helper.setTextColor(R.id.tv_finish,Util.getColor(R.color.color_999));
                helper.setBackgroundRes(R.id.dot1,R.drawable.bg__oval_colorfff_stroke_e9e9e9);
                helper.setBackgroundRes(R.id.dot2,R.drawable.bg__oval_colorfff_stroke_e9e9e9);
                helper.setBackgroundColor(R.id.line1,Util.getColor(R.color.color_e9e9e9));
                helper.setBackgroundColor(R.id.line2,Util.getColor(R.color.color_e9e9e9));
                helper.setBackgroundColor(R.id.line3,Util.getColor(R.color.color_e9e9e9));
                break;
            case 2:
                helper.setTextColor(R.id.tv_already_delivery,Util.getColor(R.color.color_6ed7af));
                helper.setTextColor(R.id.tv_finish,Util.getColor(R.color.color_999));
                helper.setBackgroundRes(R.id.dot1,R.drawable.bg__oval_colorfff_stroke_6ed7af);
                helper.setBackgroundRes(R.id.dot2,R.drawable.bg__oval_colorfff_stroke_e9e9e9);
                helper.setBackgroundColor(R.id.line1,Util.getColor(R.color.color_6ed7af));
                helper.setBackgroundColor(R.id.line2,Util.getColor(R.color.color_e9e9e9));
                helper.setBackgroundColor(R.id.line3,Util.getColor(R.color.color_e9e9e9));
                break;
            case 3:
                helper.setTextColor(R.id.tv_already_delivery,Util.getColor(R.color.color_6ed7af));
                helper.setTextColor(R.id.tv_finish,Util.getColor(R.color.color_6ed7af));
                helper.setBackgroundRes(R.id.dot1,R.drawable.bg__oval_colorfff_stroke_6ed7af);
                helper.setBackgroundRes(R.id.dot2,R.drawable.bg__oval_colorfff_stroke_6ed7af);
                helper.setBackgroundColor(R.id.line1,Util.getColor(R.color.color_6ed7af));
                helper.setBackgroundColor(R.id.line2,Util.getColor(R.color.color_6ed7af));
                helper.setBackgroundColor(R.id.line3,Util.getColor(R.color.color_6ed7af));
                break;
        }
    }
}
