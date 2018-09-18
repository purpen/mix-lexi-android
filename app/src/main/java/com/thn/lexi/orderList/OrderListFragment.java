package com.thn.lexi.orderList;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.thn.lexi.R;
import com.thn.lexi.order.OrderBean;
import com.thn.lexi.order.OrderListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends BaseFragment implements OrderListContract.View{

    private int status=0;
    private int page=1;
    private OrderListPresenter presenter;
    private WaitingDialog dialog;
    private RecyclerView recyclerView;
    private List<OrderListBean.DataBean.OrdersBean> list=new ArrayList<>();
    private AdapterOrderList adapterOrderList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_list;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        presenter=new OrderListPresenter(this);
        dialog = new WaitingDialog(getActivity());
        Bundle bundle=getArguments();
        status = bundle.getInt("key");
        recyclerView = this.getView().findViewById(R.id.recyclerView);
        adapterOrderList = new AdapterOrderList(R.layout.item_order_list,list);
        recyclerView.setAdapter(adapterOrderList);
        //上拉加载
        adapterOrderList.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },1000);
            }
        },recyclerView);
    }
    public static OrderListFragment newInstance(int id){
        OrderListFragment OrderListfragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key",id);
        OrderListfragment.setArguments(bundle);
        return OrderListfragment;
    }

    @Override
    public void loadData() {
        super.loadData();
        presenter.getData(status,page);
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
        ToastUtil.showError(error);
    }

    @Override
    public void getData(OrderListBean dataBean) {
        adapterOrderList.setNewData(dataBean.data.orders);
    }

    @Override
    public void setPresenter(OrderListContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
