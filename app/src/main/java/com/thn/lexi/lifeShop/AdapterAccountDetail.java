package com.thn.lexi.lifeShop;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.CustomLinearLayoutManager;
import com.thn.lexi.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class AdapterAccountDetail extends BaseQuickAdapter<AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderInfoBean.Bean,BaseViewHolder> {
    private Activity activity;
    public AdapterAccountDetail(int layoutResId, @Nullable List<AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderInfoBean.Bean> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountDetailBean.DataBean.LifeCashRecordDictBean.OrderInfoBean.Bean item) {
        helper.setText(R.id.tv_month,item.name);
        RecyclerView recyclerView=helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(activity));
        final AdapterAccountDetailOrder adapters=new AdapterAccountDetailOrder(R.layout.adapter_account_detail_two,item.orderBeans);
        recyclerView.setAdapter(adapters);
        helper.addOnClickListener(R.id.recyclerView);
        adapters.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tv_detail:
                        EventBus.getDefault().post(new MessageOrderId(adapters.getData().get(position).orderName,adapters.getData().get(position).commission_price));
                        break;
                }
            }
        });
    }

    class MessageOrderId{
        private String recordId;
        private double commission_price;

        MessageOrderId(String recordId, double commission_price) {
            this.recordId = recordId;
            this.commission_price = commission_price;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public double getCommission_price() {
            return commission_price;
        }

        public void setCommission_price(double commission_price) {
            this.commission_price = commission_price;
        }
    }
}
