package com.thn.lexi.lifeShop;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.thn.lexi.CustomLinearLayoutManager;
import com.thn.lexi.R;
import com.thn.lexi.view.CustomHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 账单详情
 */
public class AccountDetailActivity extends BaseActivity implements AccountDetailContract.View {

    private String rid;
    private String record_id;
    private WaitingDialog dialog;
    private AccountDetailPresenter presenter;
    private AdapterAccountDetail adapter;
    private String orderId;
    private View header;
    private TextView tv_put_money;
    private TextView tv_status;
    private TextView tv_skill_service;
    private TextView tv_put_place;
    private TextView tv_put_time;
    private ArrayList<AccountDetailMonthBean> list;
    private String orderIds;
    private double commission_price;

    @Override
    protected int getLayout() {
        return R.layout.activity_account_detail;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        rid = intent.getStringExtra("rid");
        record_id = intent.getStringExtra("record_id");
        dialog = new WaitingDialog(this);
        presenter = new AccountDetailPresenter(this);
        CustomHeadView customHeadView = findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true, Util.getString(R.string.text_account_detail));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(this));
        adapter = new AdapterAccountDetail(R.layout.adapter_account_detail, null,this);
        recyclerView.setAdapter(adapter);

        header = View.inflate(this, R.layout.adapter_account_detail_header, null);
        tv_put_money = header.findViewById(R.id.tv_put_money);
        tv_status = header.findViewById(R.id.tv_status);
        tv_skill_service = header.findViewById(R.id.tv_skill_service);
        tv_put_place = header.findViewById(R.id.tv_put_place);
        tv_put_time = header.findViewById(R.id.tv_put_time);
        presenter.loadData(rid, record_id);

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
        dialog.dismiss();
        ToastUtil.showInfo(error);
    }

    @Override
    public void setData(AccountDetailBean bean) {
        tv_put_money.setText(bean.data.life_cash_record_dict.actual_amount);
        if (1==bean.data.life_cash_record_dict.receive_target){
            tv_put_place.setText(Util.getString(R.string.text_wx_change));
        }
        switch (bean.data.life_cash_record_dict.status){
            case 1:
                tv_status.setText(Util.getString(R.string.text_examine));
                break;
            case 2:
                tv_status.setText(Util.getString(R.string.text_put_success));
                break;
            case 3:
                tv_status.setText(Util.getString(R.string.text_put_fail));
                break;
        }
        tv_skill_service.setText(bean.data.life_cash_record_dict.service_fee);
        tv_put_time.setText(DateUtil.getDateByTimestamp(bean.data.life_cash_record_dict.created_at,"yyyy-MM-dd HH:mm"));
        adapter.addHeaderView(header);
        /*Set<HashMap.Entry<String, AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderInfoBean.Bean>>
                sets = bean.data.life_cash_record_dict.order_info.bean.entrySet();
        Iterator<HashMap.Entry<String, AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderInfoBean.Bean>> its = sets.iterator();*/
        list = new ArrayList<>();
        for (Iterator iter =bean.data.life_cash_record_dict.order_info.entrySet().iterator();iter.hasNext();){
            Map.Entry element = (Map.Entry)iter.next();
            AccountDetailMonthBean monthBean=new AccountDetailMonthBean();
            monthBean.name=(String) element.getKey();
            LogUtil.e("月份："+monthBean.name);
            monthBean.ids=new ArrayList<>();
            HashMap<String,AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderBean> bean1=
                    (HashMap<String,AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderBean>)element.getValue();

            for (Iterator iter1 =bean1.entrySet().iterator();iter1.hasNext();){
                Map.Entry element1 = (Map.Entry)iter1.next();
                AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderBean bean2=
                        (AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderBean)element1.getValue();
                bean2.orderName=(String)element1.getKey();
                LogUtil.e("id:"+bean2.orderName);
                monthBean.ids.add(bean2);
            }
            list.add(monthBean);
        }
        LogUtil.e("详情的长度："+list.size());
        adapter.setNewData(list);
    }

    @Override
    public void setDetailData(AccountDetailOrderBean bean) {
        AccountDetailDialog detailDialog=new AccountDetailDialog(this,bean,commission_price);
        detailDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setPresenter(AccountDetailContract.Presenter presenter) {
        setPresenter(presenter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setOrderId(AdapterAccountDetail.MessageOrderId orderId){
        LogUtil.e("rid:"+rid+"           storeId:"+orderId.getRecordId());
        orderIds = orderId.getRecordId();
        commission_price = orderId.getCommission_price();
        presenter.loadDetailData(rid,orderIds);
    }
}
