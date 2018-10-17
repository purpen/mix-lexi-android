package com.lexivip.lexi.user.setting.userData;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.lexivip.lexi.user.login.UserProfileBean;

public class EditUserDataContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void getData(UserProfileBean bean);
        void getUpdate();
    }
    interface Presenter extends BasePresenter{
        void loadData();
        void updateData(String username, String avatar_id, String about_me, String gender, String area_id,
                        String province_id, String city_id, String mail, String date);
    }
}
