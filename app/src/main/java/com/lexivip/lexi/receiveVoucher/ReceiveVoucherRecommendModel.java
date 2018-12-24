package com.lexivip.lexi.receiveVoucher;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class ReceiveVoucherFragmentContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void getBrand(VoucherBrandBean brandBean);
        void getGoods(VoucherGoodsBean bean);
        void loadMoreFail();
        void loadMoreEnd();
        void loadMoreComplete();
        void getReceive(boolean isReceive);
        void loadOfficial();
    }
    interface Presenter extends BasePresenter{
        void loadBrand(String store_category, String page);
        void loadGoods(String store_category, String rid,String page);
        void receiveVoucher(String rid,String store_rid);
    }
}
