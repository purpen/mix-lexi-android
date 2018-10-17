package com.lexivip.lexi.lifeShop;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.basemodule.ui.BaseFragment;
import com.basemodule.ui.CustomViewPager;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * 成交订单
 */
public class TransactionOrderActivity extends BaseActivity implements View.OnClickListener,TransactionOrderContract.View {

    private TextView tv_all;
    private TextView tv_take_delivery;
    private TextView tv_unread_take_delivery;
    private TextView tv_delivery;
    private TextView tv_unread_delivery;
    private TextView tv_complete;
    private TextView tv_unread_complete;
    private TextView tv_all_deal;
    private TextView tv_day_deal;
    private CustomViewPager customViewPager;
    private String rid;
    private TransactionOrderFragment fragment0;
    private TransactionOrderFragment fragment1;
    private TransactionOrderFragment fragment2;
    private TransactionOrderFragment fragment3;
    private ArrayList<BaseFragment> fragments;
    private WaitingDialog dialog;
    private TransactionOrderPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_transaction_order;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent=getIntent();
        rid=intent.getStringExtra("rid");
        dialog = new WaitingDialog(this);
        EventBus.getDefault().register(this);
        presenter = new TransactionOrderPresenter(this);

        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,Util.getString(R.string.text_deal_order));
        tv_all = findViewById(R.id.tv_all);
        tv_take_delivery = findViewById(R.id.tv_take_delivery);
        tv_unread_take_delivery = findViewById(R.id.tv_unread_take_delivery);
        tv_delivery = findViewById(R.id.tv_delivery);
        tv_unread_delivery = findViewById(R.id.tv_unread_delivery);
        tv_complete = findViewById(R.id.tv_complete);
        tv_unread_complete = findViewById(R.id.tv_unread_complete);
        customViewPager = findViewById(R.id.customViewPager);
        tv_all_deal = findViewById(R.id.tv_all_deal);
        tv_day_deal = findViewById(R.id.tv_day_deal);
        fragments = new ArrayList<>();

        tv_all.setOnClickListener(this);
        tv_delivery.setOnClickListener(this);
        tv_take_delivery.setOnClickListener(this);
        tv_complete.setOnClickListener(this);

        fragment0 = TransactionOrderFragment.newInstance(0,rid);
        fragment1 = TransactionOrderFragment.newInstance(1,rid);
        fragment2 = TransactionOrderFragment.newInstance(2,rid);
        fragment3 = TransactionOrderFragment.newInstance(3,rid);
        fragments.add(fragment0);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        customViewPager.setAdapter(new MyFragmentPageAdapter(this.getSupportFragmentManager(), fragments));
        presenter.loadData(rid);
    }

    @Override
    public void installListener() {
        super.installListener();
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelect();
                switch (position) {
                    case 0:
                        tv_all.setTextColor(Util.getColor(R.color.color_6ed7af));
                        break;
                    case 1:
                        tv_unread_delivery.setVisibility(View.GONE);
                        tv_delivery.setTextColor(Util.getColor(R.color.color_6ed7af));
                        break;
                    case 2:
                        tv_unread_take_delivery.setVisibility(View.GONE);
                        tv_take_delivery.setTextColor(Util.getColor(R.color.color_6ed7af));
                        break;
                    case 3:
                        tv_unread_complete.setVisibility(View.GONE);
                        tv_complete.setTextColor(Util.getColor(R.color.color_6ed7af));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        setSelect();
        switch (v.getId()){
            case R.id.tv_all:
                tv_all.setTextColor(Util.getColor(R.color.color_6ed7af));
                customViewPager.setCurrentItem(0);
                break;
            case R.id.tv_delivery:
                tv_delivery.setTextColor(Util.getColor(R.color.color_6ed7af));
                tv_unread_delivery.setVisibility(View.GONE);
                customViewPager.setCurrentItem(1);
                break;
            case R.id.tv_take_delivery:
                tv_take_delivery.setTextColor(Util.getColor(R.color.color_6ed7af));
                tv_unread_take_delivery.setVisibility(View.GONE);
                customViewPager.setCurrentItem(2);
                break;
            case R.id.tv_complete:
                tv_complete.setTextColor(Util.getColor(R.color.color_6ed7af));
                tv_unread_complete.setVisibility(View.GONE);
                customViewPager.setCurrentItem(3);
                break;
        }
    }

    private void setSelect(){
        tv_all.setTextColor(Util.getColor(R.color.color_555));
        tv_delivery.setTextColor(Util.getColor(R.color.color_555));
        tv_take_delivery.setTextColor(Util.getColor(R.color.color_555));
        tv_complete.setTextColor(Util.getColor(R.color.color_555));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setNumber(TransactionRecordFragment.MessageUnreadNumber messageUnreadNumber){
        if (0!=messageUnreadNumber.getUnread0()){
            tv_unread_delivery.setVisibility(View.VISIBLE);
            tv_unread_delivery.setText(String.valueOf(messageUnreadNumber.getUnread0()));
        }else {
            tv_unread_delivery.setVisibility(View.GONE);
        }
        if (0!=messageUnreadNumber.getUnread1()){
            tv_unread_take_delivery.setVisibility(View.VISIBLE);
            tv_unread_take_delivery.setText(String.valueOf(messageUnreadNumber.getUnread1()));
        }else{
            tv_unread_take_delivery.setVisibility(View.GONE);
        }
        if (0!=messageUnreadNumber.getUnread2()){
            tv_unread_complete.setVisibility(View.VISIBLE);
            tv_unread_complete.setText(String.valueOf(messageUnreadNumber.getUnread2()));
        }else {
            tv_unread_complete.setVisibility(View.GONE);
        }
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
    public void setData(LifeShopOrderBean bean) {
        tv_all_deal.setText(String.valueOf(bean.data.all_count));
        tv_day_deal.setText(String.valueOf(bean.data.today_count
        ));
    }

    @Override
    public void setFragmentData(TransactionOrderBean bean) {

    }

    @Override
    public void loadMoreEnd() {

    }

    @Override
    public void loadMoreComplete() {

    }

    @Override
    public void setPresenter(TransactionOrderContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
