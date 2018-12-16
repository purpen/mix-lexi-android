package com.lexivip.lexi.cashMoney;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

public class CashRecordActivity extends BaseActivity implements CashRecordContract.View{

    private AdapterCashRecord adapterCashRecord;
    private CashRecordPresenter presenter;
    private WaitingDialog dialog;
    private int page=1;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_cash_record;
    }

    @Override
    public void initView() {
        super.initView();
        presenter = new CashRecordPresenter(this);
        dialog = new WaitingDialog(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_cash_record);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        linearLayout = findViewById(R.id.linearLayout);
        adapterCashRecord = new AdapterCashRecord(R.layout.adapter_account_statement_two);
        presenter.loadData(page);
    }

    @Override
    public void installListener() {
        super.installListener();
        adapterCashRecord.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page=1;
                presenter.loadData(page);
            }
        },recyclerView);
        adapterCashRecord.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(CashRecordActivity.this,CashDetailActivity.class);
                intent.putExtra("rid",adapterCashRecord.getData().get(position).rid);
                startActivity(intent);
            }
        });
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
        if (dialog!=null){
            dialog.dismiss();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void setData(CashRecordBean bean) {
        if (page==1){
            if (bean.data.record_list.isEmpty()){
                linearLayout.setVisibility(View.VISIBLE);
            }else {
                adapterCashRecord.setNewData(bean.data.record_list);
            }
        }else {
            adapterCashRecord.addData(bean.data.record_list);
        }
        page++;
    }

    @Override
    public void loadMoreEnd() {
        adapterCashRecord.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        adapterCashRecord.loadMoreComplete();
    }

    @Override
    public void loadMoreFail() {
        adapterCashRecord.loadMoreFail();
    }

    @Override
    public void setPresenter(CashAlipayConract.Presenter presenter) {
        setPresenter(presenter);
    }
}
