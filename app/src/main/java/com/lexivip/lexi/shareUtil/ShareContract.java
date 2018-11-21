package com.lexivip.lexi.shareUtil;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class ShareContract {
    interface View extends BaseView<BasePresenter>{
        void setImage(String imageUrl);
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
    }
    interface Presenter extends BasePresenter{
        void loadImageUrl(int type);
        void loadImageRidUrl(int type, String rid);
        void loadShareImage(int type,String rid,String scene);
        void loadShareWindow(String rid,String scene);
    }
}
