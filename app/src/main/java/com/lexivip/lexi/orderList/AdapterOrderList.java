package com.lexivip.lexi.orderList;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.CustomLinearLayoutManager;
import com.lexivip.lexi.ImageSizeConfig;
import com.lexivip.lexi.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterOrderList extends BaseQuickAdapter<MyOrderListBean.DataBean.OrdersBean,BaseViewHolder> {
    Calendar c= Calendar.getInstance();
    private Activity activity;
    private Date date;
    private Intent intent;

    public AdapterOrderList(int layoutResId, @Nullable List<MyOrderListBean.DataBean.OrdersBean> data,Activity activity) {
        super(layoutResId, data);
        this.activity=activity;
    }


    @Override
    protected void convert(BaseViewHolder helper, final MyOrderListBean.DataBean.OrdersBean item) {
        helper.setText(R.id.tv_shop_name,item.getStore().getStore_name());
        GlideUtil.loadImageWithFading(item.getStore().getStore_logo()+ImageSizeConfig.SIZE_AVA50,(ImageView)helper.getView(R.id.iv_shop));
        helper.setText(R.id.tv_order_money, String.valueOf(item.getPay_amount()));
        long millions=new Long(item.getCreated_at()).longValue()*1000;
        c.setTimeInMillis(millions);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = sdf.format(c.getTime());
        helper.setText(R.id.tv_order_time,dateString);
        // 1、待发货 2、待收货 3、待评价 4、待付款 5、已完成 6、已取消
        LogUtil.e("status:"+item.getUser_order_status()+"position:"+helper.getLayoutPosition());
        switch (item.getUser_order_status()){
            case 1:
                helper.setText(R.id.tv_order_status,"待发货");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                helper.setGone(R.id.bt_delete1, false);
                helper.setGone(R.id.bt_confirm,false);
                helper.setGone(R.id.bt_delete, false);
                helper.setGone(R.id.bt_evaluate,false);
                helper.setGone(R.id.bt_money, false);
                helper.setGone(R.id.bt_logistics,false);
                break;
            case 2:
                helper.setText(R.id.tv_order_status,"待收货");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                helper.setVisible(R.id.bt_confirm,true);
                if (item.getItems().size()==1) {
                    helper.setGone(R.id.bt_logistics, true);
                }else {
                    helper.setGone(R.id.bt_logistics, false);
                }
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
                helper.setGone(R.id.bt_logistics,false);
                helper.setGone(R.id.bt_money, false);
                break;
            case 4:
                date=new Date(System.currentTimeMillis());
                LogUtil.e("当前时间："+date.getTime()+"订单创建时间："+item.getCreated_at());
                GetTime getTime = null;
                LogUtil.e("间隔时间："+(date.getTime()/1000-item.getCreated_at()));
                //判断订单剩余时间，如果已经结束改变订单状态
                if((date.getTime()/1000-item.getCreated_at())<600){
                    helper.setText(R.id.tv_order_status,"待付款");
                    helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                    helper.setGone(R.id.bt_delete1, false);
                    helper.setGone(R.id.bt_confirm,false);
                    helper.setGone(R.id.bt_logistics,false);
                    helper.setGone(R.id.bt_delete, false);
                    helper.setGone(R.id.bt_evaluate,false);
                    helper.setVisible(R.id.bt_money, true);
                    date=new Date(System.currentTimeMillis());
                    if (getTime==null){
                        getTime=new GetTime((Button) helper.getView(R.id.bt_money),item.getCreated_at()+600);
                    }
                    getTime.getData(new OnRemainingTime() {
                        @Override
                        public void onEndTime(int time) {
                            if (time==0){
                                item.setUser_order_status(6);
                                notifyDataSetChanged();
                            }
                        }
                    });
                }else{
                    item.setUser_order_status(6);
                    helper.setText(R.id.tv_order_status,"交易取消");
                    helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_ff6666));
                    helper.setVisible(R.id.bt_delete1, true);
                    helper.setGone(R.id.bt_confirm,false);
                    helper.setGone(R.id.bt_logistics,false);
                    helper.setGone(R.id.bt_delete, false);
                    helper.setGone(R.id.bt_evaluate,false);
                    helper.setGone(R.id.bt_money, false);
                }
                break;
            case 5:
                helper.setText(R.id.tv_order_status,"交易成功");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_6ed7af));
                helper.setVisible(R.id.bt_delete1, true);
                helper.setGone(R.id.bt_logistics,false);
                helper.setGone(R.id.bt_confirm,false);
                helper.setGone(R.id.bt_delete, false);
                helper.setGone(R.id.bt_evaluate,false);
                helper.setGone(R.id.bt_money, false);
                break;
            case 6:
                helper.setText(R.id.tv_order_status,"交易取消");
                helper.setTextColor(R.id.tv_order_status, Util.getColor(R.color.color_ff6666));
                helper.setVisible(R.id.bt_delete1, true);
                //helper.setGone(R.id.bt_delete1, false);
                helper.setGone(R.id.bt_confirm,false);
                helper.setGone(R.id.bt_logistics,false);
                helper.setGone(R.id.bt_delete, false);
                helper.setGone(R.id.bt_evaluate,false);
                //helper.setVisible(R.id.bt_evaluate,true );
                helper.setGone(R.id.bt_money, false);
                break;
        }
        RecyclerView recyclerView=helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(activity));
        final AdapterOrderListTow adapterOrderListTow=new AdapterOrderListTow(R.layout.item_order_list_two,item.getItems(),item.getUser_order_status());
        recyclerView.setAdapter(adapterOrderListTow);
        helper.addOnClickListener(R.id.bt_confirm)
                .addOnClickListener(R.id.bt_delete)
                .addOnClickListener(R.id.bt_money)
                .addOnClickListener(R.id.bt_delete1)
                .addOnClickListener(R.id.bt_logistics)
                .addOnClickListener(R.id.bt_evaluate)
                .addOnClickListener(R.id.recyclerView);
        adapterOrderListTow.setOnItemChildClickListener(new OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.bt_logistics:
                        intent = new Intent(activity,LogisticsActivity.class);
                        intent.putExtra("logistic_code",String.valueOf(item.getItems().get(position).getExpress_no()));
                        intent.putExtra("kdn_company_code",String.valueOf(item.getItems().get(position).getExpress()));
                        intent.putExtra("order_rid",item.getRid());
                        activity.startActivity(intent);
                        break;
                }
            }
        });

        adapterOrderListTow.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtil.e("点击的具体的商品");
                intent = new Intent(activity,OrderDetailActivity.class);
                intent.putExtra("rid",item.getRid());
                activity.startActivity(intent);
            }
        });
    }
    class GetTime{
        private Date curDate;
        private OnRemainingTime onRemainingTime;
        private Button button;
        private int endTime;
        private long time;
        Handler handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                curDate = new Date(System.currentTimeMillis());
                time = endTime-(curDate.getTime()/1000);
                if (time>0) {
                    button.setText("付款 " + (time % (60 * 60) / 60) + ":" + (time % (60 * 60) % 60));
                }else{
                    onRemainingTime.onEndTime(0);
                }
                handler.sendEmptyMessageDelayed(0,1000);
            }
        };
        public GetTime(Button button, int endTime) {
            this.button=button;
            this.endTime=endTime;
            handler.sendEmptyMessage(0);
        }

        public void getData(OnRemainingTime remainingTime){
            this.onRemainingTime=remainingTime;
        }
    }

    public interface OnRemainingTime {
        /**
         * 订单付款剩余时间
         * @param time
         */
        void onEndTime(int time);

    }
}
