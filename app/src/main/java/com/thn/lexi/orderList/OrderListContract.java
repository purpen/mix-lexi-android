package com.thn.lexi.orderList;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.thn.lexi.order.OrderListBean;

import java.util.List;

public class OrderListContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void loadMoreEnd();
        void loadMoreComplete();
        void addData(List<MyOrderListBean.DataBean.OrdersBean> bean);
        void getDelete(MyOrderListBean bean);
    }
    interface Presenter extends BasePresenter {
        void getData(int status,int page);
        void deleteOrder(String rid);
    }
}
