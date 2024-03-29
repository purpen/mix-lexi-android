package com.lexivip.lexi.orderList;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.ImageSizeConfig;
import com.lexivip.lexi.R;

import java.util.List;

public class AdapterOrderListTow extends BaseQuickAdapter<MyOrderListBean.DataBean.OrdersBean.ItemsBean,BaseViewHolder>{
    int status;
    public AdapterOrderListTow(int layoutResId, @Nullable List<MyOrderListBean.DataBean.OrdersBean.ItemsBean> data,int status) {
        super(layoutResId, data);
        this.status=status;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrderListBean.DataBean.OrdersBean.ItemsBean item) {
        GlideUtil.loadImageWithFading(item.getCover()+ImageSizeConfig.SIZE_P30X2,(ImageView) helper.getView(R.id.iv_order_shoping));
        helper.setText(R.id.tv_order_shoping_name, item.getProduct_name());
        helper.setText(R.id.tv_order_shoping_num,"x"+item.getQuantity());
        if (status==2){
            /*if (helper.getAdapterPosition()!=getData().size()-1) {
                if (item.getExpress_no().equals(getData().get(helper.getAdapterPosition()+1).getExpress_no())){
                    helper.setGone(R.id.bt_logistics, false);
                }else {
                    helper.setVisible(R.id.bt_logistics, true);
                }
            }else{
                if (helper.getAdapterPosition()!=0) {
                    if (item.getExpress_no().equals(getData().get(helper.getAdapterPosition() - 1).getExpress_no())) {
                        helper.setVisible(R.id.bt_logistics, true);
                    } else {
                        helper.setGone(R.id.bt_logistics, false);
                    }
                }
            }*/
            if (getData().size()==1){
                helper.setGone(R.id.bt_logistics, false);
            }else {
                helper.setGone(R.id.bt_logistics, true);
            }
        }else {
            helper.setGone(R.id.bt_logistics, false);
        }
        helper.addOnClickListener(R.id.bt_logistics);
    }
}
