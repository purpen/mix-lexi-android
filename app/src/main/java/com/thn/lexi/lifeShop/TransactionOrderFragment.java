package com.thn.lexi.lifeShop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.thn.lexi.R;

public class TransactionOrderFragment extends BaseFragment implements TransactionOrderContract.View{
    private int page=1;
    private boolean isInit;
    private String rid;
    private RecyclerView recyclerView;
    private boolean isVisible;
    private AdapterTransactionOrder adapter;
    private WaitingDialog dialog;
    private int status;
    private TransactionOrderPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void initView() {
        super.initView();
        isInit=true;
        dialog = new WaitingDialog(getActivity());
        Bundle bundle=getArguments();
        status = bundle.getInt("key");
        rid = bundle.getString("rid");
        presenter = new TransactionOrderPresenter(this);
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterTransactionOrder(R.layout.adapter_transaction_order,null,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadFragmentData(rid,String.valueOf(status),String.valueOf(page));
            }
        },recyclerView);
        presenter.loadFragmentData(rid,String.valueOf(status),String.valueOf(page));
    }

    public static TransactionOrderFragment newInstance(int status, String rid) {
        TransactionOrderFragment transactionOrderFragment = new TransactionOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key", status);
        bundle.putString("rid", rid);
        transactionOrderFragment.setArguments(bundle);
        return transactionOrderFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible=isVisibleToUser;
        if (isVisibleToUser) {
            if (isInit) {
                page=1;
                presenter.loadFragmentData(rid,String.valueOf(status),String.valueOf(page));
            }
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
        dialog.dismiss();
        ToastUtil.showInfo(error);
    }

    @Override
    public void setData(LifeShopOrderBean bean) {

    }

    @Override
    public void setFragmentData(TransactionOrderBean bean) {
        if (page==1){
            adapter.setNewData(bean.data.orders);
        }else {
            adapter.addData(bean.data.orders);
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
    public void setPresenter(TransactionOrderContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
