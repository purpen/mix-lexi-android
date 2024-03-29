package com.lexivip.lexi.orderList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.PageUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderDetailActivity extends BaseActivity implements OrderDetailContract.View{
    private WaitingDialog dialog;
    private RecyclerView recyclerView;
    private Context context;
    private OrderDetailPresenter presenter=new OrderDetailPresenter(this);
    private View headerView;
    private View footerView;
    private TextView tv_order_code;
    private TextView tv_pay_type;
    private TextView tv_order_subtotal;
    private TextView tv_order_freight;
    private TextView tv_order_amount;
    private TextView tv_shop_name;
    private TextView tv_order_time;
    private TextView tv_order_status;
    private TextView tv_delivery_city;
    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_city;
    private TextView tv_zipcode;
    private TextView tv_mobile;
    private AdapterOrderDetail adapterOrderList;
    private LinearLayout ll_coupon_amount;
    private TextView tv_coupon_amount;
    private LinearLayout ll_reach_minus;
    private TextView tv_reach_minus;
    private LinearLayout ll_first_discount;
    private TextView tv_first_discount;

    @Override
    protected int getLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(this);
        context=OrderDetailActivity.this;
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.order_detail);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        headerView = View.inflate(context, R.layout.item_order_detail_header,null);
        footerView = View.inflate(context, R.layout.item_order_detail_footer,null);
        tv_order_code = headerView.findViewById(R.id.tv_order_code);
        tv_pay_type = headerView.findViewById(R.id.tv_pay_type);
        tv_order_subtotal = headerView.findViewById(R.id.tv_order_subtotal);
        tv_order_freight = headerView.findViewById(R.id.tv_order_freight);
        tv_order_amount = headerView.findViewById(R.id.tv_order_amount);
        tv_shop_name = headerView.findViewById(R.id.tv_shop_name);
        tv_order_time = headerView.findViewById(R.id.tv_order_time);
        tv_order_status = headerView.findViewById(R.id.tv_order_status);
        tv_delivery_city = headerView.findViewById(R.id.tv_delivery_city);

        ll_coupon_amount = headerView.findViewById(R.id.ll_coupon_amount);
        tv_coupon_amount = headerView.findViewById(R.id.tv_coupon_amount);
        ll_reach_minus = headerView.findViewById(R.id.ll_reach_minus);
        tv_reach_minus = headerView.findViewById(R.id.tv_reach_minus);
        ll_first_discount = headerView.findViewById(R.id.ll_first_discount);
        tv_first_discount = headerView.findViewById(R.id.tv_first_discount);

        tv_name = footerView.findViewById(R.id.tv_name);
        tv_address = footerView.findViewById(R.id.tv_address);
        tv_city = footerView.findViewById(R.id.tv_city);
        tv_zipcode = footerView.findViewById(R.id.tv_zipcode);
        tv_mobile = footerView.findViewById(R.id.tv_mobile);

        Intent intent=getIntent();
        presenter.getData(intent.getStringExtra("rid"));

    }

    @Override
    public void installListener() {
        super.installListener();
    }

    public void adsfasd(){

    }

    class ceshi{
        public void sa(){
            adsfasd();
        }
    }

    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.dismiss();
    }

    @Override
    public void showError(String error) {
        ToastUtil.showInfo(error);
    }

    @Override
    public void getData(OrderDetailBean bean) {
        if (bean.success) {
            adapterOrderList = new AdapterOrderDetail(R.layout.item_order_detail, bean.data.getItems(),bean.data.getUser_order_status());
            adapterOrderList.addHeaderView(headerView);
            adapterOrderList.addFooterView(footerView);
            LogUtil.e("订单状态："+bean.data.getUser_order_status());
            // 1、待发货 2、待收货 3、待评价 4、待付款 5、已完成 6、已取消
            if (0==bean.data.getUser_order_status()||6==bean.data.getUser_order_status()){
                // for (int i=0;i<bean.data.getItems().size();i++)
                // bean.data.getItems().get(i).isShow=false;
            }else{
                if(bean.data.isIs_many_express()){
                    bean.data.getItems().get(bean.data.getItems().size()-1).isShow = true;
                }else {
                    for (int i=0;i<bean.data.getItems().size();i++){
                        if (bean.data.getItems().size()!=(i+1)){
                            if (bean.data.getItems().get(i + 1).getExpress() == bean.data.getItems().get(i).getExpress()) {
                                //  bean.data.getItems().get(i).isShow=false;
                            } else {
                                bean.data.getItems().get(i).isShow = true;
                            }
                        }
                    }
                }

            }
            tv_order_code.setText(String.valueOf(bean.data.getRid()));
            tv_order_subtotal.setText("¥"+bean.data.getTotal_amount());
            if (0==bean.data.getFreight()) {
                tv_order_freight.setText("包邮");
            }else{
                tv_order_freight.setText(String.valueOf(bean.data.getFreight()));
            }
            tv_order_amount.setText("¥"+bean.data.getUser_pay_amount());
            tv_shop_name.setText(bean.data.getStore().getStore_name());

            if(0.00!=bean.data.getCoupon_amount()||0.00!=bean.data.getOfficial_bonus_amount()){
                ll_coupon_amount.setVisibility(View.VISIBLE);
                if (0.00!=bean.data.getCoupon_amount()) {
                    tv_coupon_amount.setText("￥-"+bean.data.getCoupon_amount());
                }else {
                    tv_coupon_amount.setText("￥-"+bean.data.getOfficial_bonus_amount());
                }
            }
            if (0.00!=bean.data.getFirst_discount()){
                ll_first_discount.setVisibility(View.VISIBLE);
                tv_first_discount.setText("￥-"+bean.data.getFirst_discount());
            }
            if (0.00!=bean.data.getReach_minus()){
                ll_reach_minus.setVisibility(View.VISIBLE);
                tv_reach_minus.setText("￥-"+bean.data.getFirst_discount());
            }

            Calendar c= Calendar.getInstance();
            long millions=new Long(bean.data.getCreated_at()).longValue()*1000;
            c.setTimeInMillis(millions);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String dateString = sdf.format(c.getTime());
            tv_order_time.setText(dateString);
            // 1、待发货 2、待收货 3、待评价 4、待付款 5、已完成 6、已取消
            switch (bean.data.getUser_order_status()){
                case 1:
                    tv_order_status.setText("待发货");
                    break;
                case 2:
                    tv_order_status.setText("待收货");
                    break;
                case 3:
                    tv_order_status.setText("待评价");
                    break;
                case 5:
                    tv_order_status.setText("已完成");
                    break;
                case 6:
                    tv_order_status.setText("已取消");
                    break;
                case 4:
                    tv_order_status.setText("待付款");
                    break;
            }
            tv_delivery_city.setText("从"+bean.data.getItems().get(0).getDelivery_province()+"发货");
            if (1==bean.data.getPay_type()) {
                tv_pay_type.setText(AppApplication.getContext().getString(R.string.order_pay_type_wx));
            }else {
                tv_pay_type.setText(AppApplication.getContext().getString(R.string.text_ali_pay));
            }

            tv_name.setText(bean.data.getBuyer_name());
            tv_mobile.setText(bean.data.getBuyer_phone());
            tv_address.setText(bean.data.getBuyer_address());
            tv_city.setText(bean.data.getBuyer_province()+"，"+bean.data.getBuyer_city()+"，"+bean.data.getBuyer_area());
            if (!bean.data.getBuyer_zipcode().isEmpty())
                tv_zipcode.setText(bean.data.getBuyer_zipcode());

            recyclerView.setAdapter(adapterOrderList);

            adapterOrderList.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId()==R.id.bt_logistics){
                        Intent intent=new Intent(OrderDetailActivity.this,LogisticsActivity.class);
                        /*intent.putExtra("logistic_code", String.valueOf(adapterOrderList.getData().get(position).getExpress_no()));
                        intent.putExtra("kdn_company_code", String.valueOf(adapterOrderList.getData().get(position).getExpress()));
                        intent.putExtra("order_rid",adapterOrderList.getData().get(position).getRid());*/

                        intent.putExtra("logistic_code",String.valueOf(adapterOrderList.getData().get(position).getExpress_no()));
                        intent.putExtra("kdn_company_code",String.valueOf(adapterOrderList.getData().get(position).getExpress_code()));
                        intent.putExtra("order_rid",adapterOrderList.getItem(position).getRid());
                        intent.putExtra("express_name",adapterOrderList.getData().get(position).getExpress_name());
                        startActivity(intent);
                    }
                }
            });
            adapterOrderList.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    PageUtil.jump2GoodsDetailActivity(adapterOrderList.getData().get(position).getProduct_rid());
                }
            });
        }else{
            ToastUtil.showInfo(AppApplication.getContext().getString(R.string.text_net_error));
        }
    }
}
