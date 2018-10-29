package com.lexivip.lexi.receiveVoucher;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class ReceiveVoucherContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void getCategories(VoucherCategoriesBean bean);
        void getNotice(VoucherNoticeBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadClass();
        void loadNotice();
    }
}
