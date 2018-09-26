package com.thn.lexi.orderList;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class LogisticsContract {
    interface View extends BaseView<Presenter> {
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void shoeNull();
        void showList(LogisticsBean.DataBean bean);
    }
    interface Presenter extends BasePresenter {
        void getData(String logistic_code,String kdn_company_code);
    }
}
