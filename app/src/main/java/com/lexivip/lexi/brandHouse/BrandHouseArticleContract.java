package com.lexivip.lexi.brandHouse;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class BrandHouseArticleContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void loadMoreFail();
        void loadMoreEnd();
        void loadMoreComplete();
        void setArticle(BrandHouseArticelBean bean);
        void addArticle(BrandHouseArticelBean bean);
        void showNull();
    }
    interface Presenter extends BasePresenter{
        void loadArticle(String rid,String page);
    }
}
