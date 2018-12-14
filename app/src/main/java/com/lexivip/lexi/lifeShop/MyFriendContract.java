package com.lexivip.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class MyFriendContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void loadMoreEnd();
        void loadMoreComplete();
        void loadMoreFail();
        void showError(String error);
        void setData(MyFriendBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(int page);
    }
}
