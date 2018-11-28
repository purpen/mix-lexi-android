package com.lexivip.lexi.dialog;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class CouponContract {
    interface View extends BaseView<Presenter>{
        void loadData();
    }
    interface Presenter extends BasePresenter{
        void loadData();
    }
}
