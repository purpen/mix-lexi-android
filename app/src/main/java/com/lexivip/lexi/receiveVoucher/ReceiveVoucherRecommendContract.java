package com.lexivip.lexi.receiveVoucher;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class ReceiveVoucherRecommendContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void getImage();
        void getOfficial(VoucherOfficialBean bean);
        void getBrand(VoucherBrandBean brandBean);
        void getGoods(VoucherGoodsBean bean);
        void getReceive(boolean isReceive);
        void getReceiveOfficial(boolean is_grant);
        void loadMoreFail();
        void loadMoreEnd();
        void loadMoreComplete();
    }
    interface Presenter extends BasePresenter{
        void loadBrand(String store_category);
        void loadGoods(String store_category, String rid,String page);
        void loadImage();
        void loadOfficial();
        void receiveVoucher(String rid,String store_rid);
        void receiveOfficial(String rid);
    }
}
