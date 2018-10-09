package com.thn.lexi.lifeShop;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.thn.lexi.CustomLinearLayoutManager;
import com.thn.lexi.R;
import com.thn.lexi.view.CustomHeadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 对账单
 */
public class AccountStatementActivity extends BaseActivity implements AccountStatementContract.View{
    private int page=1;
    private String rid;
    private AdapterAccountStatement adapter;
    private WaitingDialog dialog;
    private AccountStatementPresenter presenter;
    private ArrayList<AccountStatementBean.DataBean.StatementsBeanX> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_account_statement;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent=getIntent();
        rid = intent.getStringExtra("rid");
        dialog = new WaitingDialog(this);
        presenter = new AccountStatementPresenter(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,Util.getString(R.string.text_statements));
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(this));
        adapter = new AdapterAccountStatement(R.layout.adapter_account_statement,null,this,rid);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadData(rid,String.valueOf(page));
            }
        },recyclerView);
        presenter.loadData(rid,String.valueOf(page));
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
    public void setData(AccountStatementBean bean) {
        list=new ArrayList<>();
        Set<HashMap.Entry<String, AccountStatementBean.DataBean.StatementsBeanX>> eSet  =  bean.data.statements.entrySet();
        Iterator<HashMap.Entry<String, AccountStatementBean.DataBean.StatementsBeanX>> it = eSet.iterator();
        while (it.hasNext()){
            AccountStatementBean.DataBean.StatementsBeanX beanX=it.next().getValue();
            beanX.name=it.next().getKey();
            list.add(beanX);
        }
        if (page==1){
            adapter.setNewData(list);
        }else {
            adapter.addData(list);
        }
        page++;
    }

    @Override
    public void loadMoreEnd() {
        adapter.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        adapter.loadMoreComplete();
    }

    @Override
    public void setPresenter(AccountStatementContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
