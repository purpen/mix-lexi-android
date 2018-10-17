package com.lexivip.lexi.orderList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.CustomLinearLayoutManager;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrderListFragment extends BaseFragment implements OrderListContract.View{

    private int status=0;
    private int page=1;
    private OrderListPresenter presenter=new OrderListPresenter(this);
    private WaitingDialog dialog;
    private RecyclerView recyclerView;
    private AdapterOrderList adapterOrderList;
    private boolean isVisibleToUser;
    private boolean isInit;
    private int positions;
    private Intent intent;
    private InquiryDialog inquiryDialog;


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
        isInit=true;
        Bundle bundle=getArguments();
        dialog = new WaitingDialog(getActivity());
        status = bundle.getInt("key");
        recyclerView = this.getView().findViewById(R.id.recyclerView);
        adapterOrderList = new AdapterOrderList(R.layout.item_order_list,null,getActivity());
        //上拉加载
        adapterOrderList.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.getData(status,page);
            }
        },recyclerView);
        if (isVisibleToUser)
        presenter.getData(status,page);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterOrderList);


        adapterOrderList.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                positions=position;
                switch (view.getId()){
                    case R.id.bt_delete1:
                    case R.id.bt_delete:
                        LogUtil.e("删除订单啊啊啊啊啊啊啊");
                        inquiryDialog=new InquiryDialog("确定删除订单？", getContext(), new InquiryDialog.ImagePopwindowInterface() {
                            @Override
                            public void getCheck(boolean isCheck) {
                                if (isCheck){
                                    presenter.deleteOrder(adapterOrderList.getData().get(position).getRid());
                                }
                            }
                        });

                        break;
                    case R.id.bt_evaluate:
                        LogUtil.e("评价订单啊啊啊啊啊啊啊");
                        intent=new Intent(getActivity(),EvaluateActivity.class);
                        intent.putExtra(EvaluateActivity.class.getSimpleName(),adapterOrderList.getData().get(position));
                        getActivity().startActivity(intent);
                        break;
                    case R.id.bt_confirm:
                        LogUtil.e("收货订单啊啊啊啊啊啊啊");
                        inquiryDialog = new InquiryDialog("确认收货？", getContext(), new InquiryDialog.ImagePopwindowInterface() {
                            @Override
                            public void getCheck(boolean isCheck) {
                                if (isCheck){
                                    presenter.deleteOrder(adapterOrderList.getData().get(position).getRid());
                                }
                            }
                        });

                        break;
                    case R.id.bt_money:
                        //todo 订单支付待完成
                        LogUtil.e("支付订单啊啊啊啊啊啊啊");
                        break;
                    case R.id.bt_logistics:
                        //todo 物流跟踪
                        LogUtil.e("物流订单啊啊啊啊啊啊啊");
                        break;
                }
            }
        });

    }
    public static OrderListFragment newInstance(int id){
        OrderListFragment OrderListfragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key",id);
        OrderListfragment.setArguments(bundle);
        return OrderListfragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        if (isVisibleToUser) {
            if (isInit) {
                page=1;
                LogUtil.e("当前页面的：" + status + "      页码：" + page);
                presenter.getData(status, page);
            }
        }
    }

    @Override
    public void loadData() {
        super.loadData();
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
    public void loadMoreEnd() {
        adapterOrderList.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        adapterOrderList.loadMoreComplete();
    }

    @Override
    public void loadMoreFail() {
        adapterOrderList.loadMoreFail();
    }

    @Override
    public void addData(List<MyOrderListBean.DataBean.OrdersBean> bean) {
        if (1==page) {
            adapterOrderList.setNewData(bean);
        }else{
            adapterOrderList.addData(bean);
        }
            page++;
    }

    @Override
    public void getDelete() {
        adapterOrderList.remove(positions);
    }

    @Override
    public void getFinish() {
        adapterOrderList.getData().get(positions).setUser_order_status(5);
        adapterOrderList.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(OrderListContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
