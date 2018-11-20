package com.lexivip.lexi.shareUtil;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class ShareContract {
    interface View extends BaseView<BasePresenter>{
        void setImage(String imageUrl);
        void setAuthApp();
    }
    interface Presenter extends BasePresenter{
        void loadImageUrl();
        void loadImageRidUrl(String rid);
    }
}
