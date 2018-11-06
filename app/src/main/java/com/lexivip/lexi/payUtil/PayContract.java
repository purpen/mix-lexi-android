package com.lexivip.lexi.payUtil;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class PayContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void getPayOrder(PayWXBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadWXPayOrder(String rid,int pay_type,int type);
    }
}
