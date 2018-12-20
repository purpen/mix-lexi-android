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
        void setMarket(String marketUrl);
    }
    interface Presenter extends BasePresenter{
        void loadShareImage(String pageUrl,int type,String rid,String scene);
        void loadShareWindow(String rid,String scene);
        void loadShareInvitation(String scene);
        void loadShareMarket(String rid,int type);
        void loadFriend();
        void loadBrand(String rid);
        void loadInvitation();
    }
}
