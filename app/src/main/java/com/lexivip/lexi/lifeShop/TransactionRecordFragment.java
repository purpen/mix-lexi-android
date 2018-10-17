package com.lexivip.lexi.lifeShop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;

import org.greenrobot.eventbus.EventBus;

public class TransactionRecordFragment extends BaseFragment implements TransactionRecordContract.View {

    private int status;
    private RecyclerView recyclerView;
    private AdapterTransactionRecord adapter;
    private TransactionRecordPresenter presenter = new TransactionRecordPresenter(this);
    private WaitingDialog dialog;
    private static String date_range;
    private int page=1;
    private boolean isInit;
    private boolean isVisible;
    private String rid;

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.builder().build();
        isInit=true;
        dialog = new WaitingDialog(getActivity());
        Bundle bundle = getArguments();
        status = bundle.getInt("key");
        rid = bundle.getString("rid");
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterTransactionRecord(R.layout.adapter_transaction_record, null, getActivity());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadFragmentData(date_range, rid,String.valueOf(status),String.valueOf(page));
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
        presenter.loadFragmentData(date_range, rid,String.valueOf(status),String.valueOf(page));
    }

    public static TransactionRecordFragment newInstance(int status, String rid) {
        TransactionRecordFragment TransactionRecordFragment = new TransactionRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key", status);
        bundle.putString("rid", rid);
        TransactionRecordFragment.setArguments(bundle);
        return TransactionRecordFragment;
    }

    public void setRange(String stringRange) {
        date_range = stringRange;
        if (isVisible)
            if (isInit)
                presenter.loadFragmentData(date_range, rid,String.valueOf(status),String.valueOf(page));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible=isVisibleToUser;
        if (isVisibleToUser) {
            if (isInit) {
                page=1;
                LogUtil.e("当前页面的：" + status + "      页码：" + page);
                presenter.loadFragmentData(date_range, rid,String.valueOf(status),String.valueOf(page));
            }
        }
    }

    @Override
    public void setPresenter(TransactionRecordContract.Presenter presenter) {
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
        dialog.dismiss();
        ToastUtil.showError(error);
    }

    @Override
    public void setData(LifeShopSaleBean bean) {

    }

    @Override
    public void setFragmentData(TransactionRecordBean bean) {
        EventBus.getDefault().post(new MessageUnreadNumber(bean.data.not_settlement_not_read,bean.data.success_not_read,bean.data.refund_not_read));
        if (1==page){
            adapter.setNewData(bean.data.transactions);
        }else {
            adapter.addData(bean.data.transactions);
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

    class MessageUnreadNumber{
        private int unread0;
        private int unread1;
        private int unread2;

        public int getUnread0() {
            return unread0;
        }

        public void setUnread0(int unread0) {
            this.unread0 = unread0;
        }

        public int getUnread1() {
            return unread1;
        }

        public void setUnread1(int unread1) {
            this.unread1 = unread1;
        }

        public int getUnread2() {
            return unread2;
        }

        public void setUnread2(int unread2) {
            this.unread2 = unread2;
        }

        public MessageUnreadNumber(int loading, int success, int refund) {
            this.unread0 = loading;
            this.unread1 = success;
            this.unread2 = refund;
        }
    }
}
