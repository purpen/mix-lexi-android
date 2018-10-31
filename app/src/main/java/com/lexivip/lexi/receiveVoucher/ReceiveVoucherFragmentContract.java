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
    }
    interface Presenter extends BasePresenter{
        void loadBrand(String store_category, String page);
        void loadGoods(String store_category, String rid);
    }
}
