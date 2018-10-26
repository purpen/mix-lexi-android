package com.lexivip.lexi.user.setting.address;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.lexivip.lexi.order.UserAddressListBean;

public class AddressListContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void getData(UserAddressListBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData();
    }
}
