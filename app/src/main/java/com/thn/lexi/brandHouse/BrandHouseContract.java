package com.thn.lexi.brandHouse;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class BrandHouseContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void setData(BrandHouseBean bean);
        void setNoticeData(BrandHouseNoticeBean bean);

    }
    interface Presenter extends BasePresenter{
        void loadData(String rid);
        void loadNoticeData(String rid);
    }
}
