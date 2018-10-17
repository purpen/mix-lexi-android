package com.lexivip.lexi.orderList;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

import java.util.List;

public class OrderListContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void loadMoreEnd();
        void loadMoreComplete();
        void loadMoreFail();
        void addData(List<MyOrderListBean.DataBean.OrdersBean> bean);
        void getDelete();
        void getFinish();
    }
    interface Presenter extends BasePresenter {
        void getData(int status,int page);
        void deleteOrder(String rid);
        void finishOrder(String rid);
    }
}
