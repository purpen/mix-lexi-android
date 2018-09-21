package com.thn.lexi.orderList;

import android.app.backup.BackupHelper;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.CustomLinearLayoutManager;
import com.thn.lexi.R;
import com.thn.lexi.order.OrderBean;
import com.thn.lexi.order.OrderListBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdapterOrderList extends BaseQuickAdapter<MyOrderListBean.DataBean.OrdersBean,BaseViewHolder> {
    Calendar c= Calendar.getInstance();
    private Button button;
    private long between;
    private Context context;
    public AdapterOrderList(int layoutResId, @Nullable List data,Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (between>0) {
                button.setText("付款 " + (between % (60 * 60) / 60) + ":" + (between % (60 * 60) % 60));
                handler.sendEmptyMessageDelayed(0, 1000);
            }else{
            }
        }
    };
    @Override
    protected void convert(BaseViewHolder helper, MyOrderListBean.DataBean.OrdersBean item) {
        helper.setText(R.id.tv_shop_name,item.getStore().getStore_name());
        GlideUtil.loadImage(item.getStore().getStore_logo(),(ImageView)helper.getView(R.id.iv_shop));
        helper.setText(R.id.tv_order_money, String.valueOf(item.getPay_amount()));
        long millions=new Long(item.getCreated_at()).longValue()*1000;
        c.setTimeInMillis(millions);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = sdf.format(c.getTime());
        helper.setText(R.id.tv_order_time,dateString);
        // 1、待发货 2、待收货 3、待评价 4、待付款 5、已完成 6、已取消
        switch (item.getUser_order_status()){
            case 1:
                helper.setText(R.id.tv_order_status,"待发货");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                helper.setGone(R.id.bt_delete1, true);
                helper.setGone(R.id.bt_confirm,true);
                helper.setGone(R.id.bt_delete, true);
                helper.setGone(R.id.bt_evaluate,true);
                helper.setGone(R.id.bt_money, true);
                break;
            case 2:
                helper.setText(R.id.tv_order_status,"待收货");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                helper.setVisible(R.id.bt_confirm,true);
                helper.setGone(R.id.bt_delete1, false);
                helper.setGone(R.id.bt_delete, false);
                helper.setGone(R.id.bt_evaluate,false);
                helper.setGone(R.id.bt_money, false);
                break;
            case 3:
                helper.setText(R.id.tv_order_status,"交易成功");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                helper.setVisible(R.id.bt_delete, true);
                helper.setVisible(R.id.bt_evaluate,true);
                helper.setGone(R.id.bt_delete1, false);
                helper.setGone(R.id.bt_confirm,false);
                helper.setGone(R.id.bt_money, false);
                break;
            case 4:
                helper.setText(R.id.tv_order_status,"待付款");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                helper.setGone(R.id.bt_delete1, false);
                helper.setGone(R.id.bt_confirm,false);
                helper.setGone(R.id.bt_delete, false);
                helper.setGone(R.id.bt_evaluate,false);
                helper.setVisible(R.id.bt_money, true);
                between=Long.valueOf(600-((item.getCurrent_time()-item.getCreated_at())/1000));
                button=helper.getView(R.id.bt_money);
                handler.sendEmptyMessage(0);
                break;
            case 5:
                helper.setText(R.id.tv_order_status,"交易成功");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                helper.setVisible(R.id.bt_delete1, true);

                helper.setGone(R.id.bt_confirm,false);
                helper.setGone(R.id.bt_delete, false);
                helper.setGone(R.id.bt_evaluate,false);
                helper.setGone(R.id.bt_money, false);
                break;
            case 6:
                helper.setText(R.id.tv_order_status,"交易取消");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_ff6666));
                helper.setVisible(R.id.bt_delete1, true);
                helper.setGone(R.id.bt_confirm,false);
                helper.setGone(R.id.bt_delete, false);
                helper.setGone(R.id.bt_evaluate,false);
                helper.setGone(R.id.bt_money, false);
                break;
        }
        RecyclerView recyclerView=helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(context));
        recyclerView.setAdapter(new AdapterOrderListTow(R.layout.item_order_list_two,item.getItems(),item.getUser_order_status()));
        helper.addOnClickListener(R.id.bt_confirm)
                .addOnClickListener(R.id.bt_delete)
                .addOnClickListener(R.id.bt_money)
                .addOnClickListener(R.id.bt_delete1)
                .addOnClickListener(R.id.bt_logistics)
                .addOnClickListener(R.id.recyclerView);
    }
}
