package com.lexivip.lexi.brandHouse;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class AboutBrandHouseContract {
    interface View extends BaseView<presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void setOwnerData(AboutBrandHouseOwnerBean bean);
        void setDetailData(AboutBrandHouseDetailBean bean);
    }
    interface presenter extends BasePresenter{
        void loadOwnerData(String rid);
        void loadDetailData(String rid);
    }
}
