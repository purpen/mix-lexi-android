package com.lexivip.lexi.brandHouse;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.beans.ProductBean;
import com.lexivip.lexi.index.detail.ShopCouponListBean;

import java.util.List;

public class BrandHouseContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void setData(BrandHouseBean bean);
        void setNoticeData(BrandHouseNoticeBean bean);
        void setCouponsData(ShopCouponListBean bean);
        void setIsFollow(BrandHouseFollowBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid);
        void loadNoticeData(String rid);
        void loadCouponsData(String rid);
        void followStore(String rid);
        void unFollowStore(String rid);
    }
}
