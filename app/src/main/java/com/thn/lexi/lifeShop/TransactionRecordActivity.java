package com.thn.lexi.lifeShop;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.basemodule.ui.BaseFragment;
import com.basemodule.ui.CustomFragmentPagerAdapter;
import com.basemodule.ui.CustomViewPager;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.thn.lexi.R;
import com.thn.lexi.view.CustomHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 交易记录
 */
public class TransactionRecordActivity extends BaseActivity implements View.OnClickListener,TransactionRecordContract.View {

    private CustomHeadView customHeadView;
    private TextView tv_time;
    private TextView tv_seven_day;
    private TextView tv_thirty_day;
    private TextView tv_all;
    private TextView tv_loading;
    private TextView tv_unread_loading;
    private TextView tv_success;
    private TextView tv_unread_success;
    private TextView tv_refund;
    private TextView tv_unread_refund;
    private CustomViewPager customViewPager;
    private ImageView iv_money_show;
    private TextView tv_gross_earnings;
    private TextView tv_day_money;
    private TextView tv_loading_money;
    private ImageView iv_problem;
    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private TransactionRecordFragment fragment0;
    private TransactionRecordFragment fragment1;
    private TransactionRecordFragment fragment2;
    private TransactionRecordFragment fragment3;
    private Calendar selectData;
    private Date selectTime;
    private boolean isShowSale=true;
    private WaitingDialog dialog;
    private String saleMoney;
    private String rId;
    private TransactionRecordPresenter presenter=new TransactionRecordPresenter(this);

    @Override
    protected int getLayout() {
        return R.layout.activity_transaction_record;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent=getIntent();
        rId = intent.getStringExtra("rid");
        EventBus.getDefault().register(this);
        dialog = new WaitingDialog(this);
        customHeadView = findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true, Util.getString(R.string.text_transaction_record));
        tv_time = findViewById(R.id.tv_time);
        tv_seven_day = findViewById(R.id.tv_seven_day);
        tv_thirty_day = findViewById(R.id.tv_thirty_day);
        tv_all = findViewById(R.id.tv_all);
        tv_loading = findViewById(R.id.tv_loading);
        tv_unread_loading = findViewById(R.id.tv_unread_loading);
        tv_success = findViewById(R.id.tv_success);
        tv_unread_success = findViewById(R.id.tv_unread_success);
        tv_refund = findViewById(R.id.tv_refund);
        tv_unread_refund = findViewById(R.id.tv_unread_refund);
        customViewPager = findViewById(R.id.customViewPager);

        iv_money_show = findViewById(R.id.iv_money_show);
        tv_gross_earnings = findViewById(R.id.tv_gross_earnings);
        tv_day_money = findViewById(R.id.tv_day_money);
        tv_loading_money = findViewById(R.id.tv_loading_money);
        iv_problem = findViewById(R.id.iv_problem);

        tv_time.setOnClickListener(this);
        tv_seven_day.setOnClickListener(this);
        tv_thirty_day.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_loading.setOnClickListener(this);
        tv_success.setOnClickListener(this);
        tv_refund.setOnClickListener(this);
        iv_money_show.setOnClickListener(this);
        iv_problem.setOnClickListener(this);

        fragment0 = TransactionRecordFragment.newInstance(0,rId);
        fragment0.setRange("week");
        fragment1 = TransactionRecordFragment.newInstance(1,rId);
        fragment1.setRange("week");
        fragment2 = TransactionRecordFragment.newInstance(2,rId);
        fragment2.setRange("week");
        fragment3 = TransactionRecordFragment.newInstance(3,rId);
        fragment3.setRange("week");
        fragments.add(fragment0);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        customViewPager.setAdapter(new CustomFragmentPagerAdapter(this.getSupportFragmentManager(), fragments, null));
        presenter.loadData(rId);
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
                        tv_all.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                        break;
                    case 1:
                        tv_unread_loading.setVisibility(View.GONE);
                        tv_loading.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                        break;
                    case 2:
                        tv_unread_success.setVisibility(View.GONE);
                        tv_success.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                        break;
                    case 3:
                        tv_unread_refund.setVisibility(View.GONE);
                        tv_refund.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
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
        switch (v.getId()) {
            case R.id.tv_time:
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                startDate.set(2017, 0, 1);
                selectData = Calendar.getInstance();
                if (selectTime != null) {
                    selectData.setTime(selectTime);
                }
                endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        selectTime = date;
                        tv_time.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                        String dateString=DateUtil.getDateString(date);
                        tv_time.setText(dateString);
                        tv_seven_day.setBackgroundColor(Util.getColor(R.color.color_959fa7));
                        tv_thirty_day.setBackgroundColor(Util.getColor(R.color.color_959fa7));
                        fragment0.setRange(dateString);
                        fragment1.setRange(dateString);
                        fragment2.setRange(dateString);
                        fragment3.setRange(dateString);
                    }
                })
                        .setRangDate(startDate, endDate)
                        .setDate(selectData)
                        .build();
                break;
            case R.id.tv_seven_day:
                tv_time.setText(Util.getString(R.string.text_select_time));
                tv_time.setBackgroundColor(Util.getColor(R.color.color_959fa7));
                tv_seven_day.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                tv_thirty_day.setBackgroundColor(Util.getColor(R.color.color_959fa7));
                fragment0.setRange("week");
                fragment1.setRange("week");
                fragment2.setRange("week");
                fragment3.setRange("week");
                break;
            case R.id.tv_thirty_day:
                tv_time.setText(Util.getString(R.string.text_select_time));
                tv_time.setBackgroundColor(Util.getColor(R.color.color_959fa7));
                tv_seven_day.setBackgroundColor(Util.getColor(R.color.color_959fa7));
                tv_thirty_day.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                fragment0.setRange("month");
                fragment1.setRange("month");
                fragment2.setRange("month");
                fragment3.setRange("month");
                break;
            case R.id.tv_all:
                setSelect();
                tv_all.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                customViewPager.setCurrentItem(0);
                break;
            case R.id.tv_loading:
                setSelect();
                tv_loading.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                customViewPager.setCurrentItem(1);
                tv_unread_loading.setVisibility(View.GONE);
                break;
            case R.id.tv_success:
                setSelect();
                tv_success.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                customViewPager.setCurrentItem(2);
                tv_unread_success.setVisibility(View.GONE);
                break;
            case R.id.tv_refund:
                setSelect();
                tv_refund.setBackgroundColor(Util.getColor(R.color.color_6ed7af));
                customViewPager.setCurrentItem(3);
                tv_unread_refund.setVisibility(View.GONE);
                break;
            case R.id.iv_money_show:
                if (isShowSale){
                    iv_money_show.setBackgroundResource(R.mipmap.icon_live_hidden_money);
                    isShowSale=false;
                    tv_gross_earnings.setText("***");
                }else{
                    iv_money_show.setBackgroundResource(R.mipmap.icon_live_show_money);
                    isShowSale=true;
                    if (saleMoney.isEmpty()){
                        tv_gross_earnings.setText("0.00");
                    }else{
                        tv_gross_earnings.setText(saleMoney);
                    }
                }
                break;
            case R.id.iv_problem:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage(Util.getString(R.string.text_pending_cash));
                builder.create().show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setNumber(TransactionRecordFragment.MessageUnreadNumber messageUnreadNumber){
        if (0!=messageUnreadNumber.getUnread0()){
            tv_unread_loading.setVisibility(View.VISIBLE);
            tv_unread_loading.setText(String.valueOf(messageUnreadNumber.getUnread0()));
        }else {
            tv_unread_loading.setVisibility(View.GONE);
        }
        if (0!=messageUnreadNumber.getUnread1()){
            tv_unread_success.setVisibility(View.VISIBLE);
            tv_unread_success.setText(String.valueOf(messageUnreadNumber.getUnread1()));
        }else{
            tv_unread_success.setVisibility(View.GONE);
        }
        if (0!=messageUnreadNumber.getUnread2()){
            tv_unread_refund.setVisibility(View.VISIBLE);
            tv_unread_refund.setText(String.valueOf(messageUnreadNumber.getUnread2()));
        }else {
            tv_unread_refund.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setSelect() {
        tv_all.setBackgroundColor(Util.getColor(R.color.color_555));
        tv_loading.setBackgroundColor(Util.getColor(R.color.color_555));
        tv_success.setBackgroundColor(Util.getColor(R.color.color_555));
        tv_refund.setBackgroundColor(Util.getColor(R.color.color_555));
    }

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.show();
    }

    @Override
    public void showError(String error) {
        dialog.dismiss();
        ToastUtil.showError(error);
    }

    @Override
    public void setData(LifeShopSaleBean bean) {
        saleMoney = bean.data.total_commission_price;
        if (isShowSale)
            tv_gross_earnings.setText(bean.data.total_commission_price);
        tv_day_money.setText(String.valueOf(bean.data.today_commission_price));
        tv_loading_money.setText(String.valueOf(bean.data.pending_commission_price));
    }

    @Override
    public void setFragmentData(TransactionRecordBean bean) {

    }

    @Override
    public void loadMoreEnd() {

    }

    @Override
    public void loadMoreComplete() {

    }

    @Override
    public void setPresenter(TransactionRecordContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
