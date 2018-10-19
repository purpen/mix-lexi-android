package com.lexivip.lexi.user.setting.userData;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.lexivip.lexi.address.CityBean;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;
import com.lexivip.lexi.user.login.UserProfileBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class EditUserDataContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void getData(UserProfileBean bean);
        void getUpdate();
        void getImage(JSONArray ids);
        void setToken(UploadTokenBean bean);
        void getCity(HashMap<String,ArrayList<CityBean.CityNameBean>> map);
    }
    interface Presenter extends BasePresenter{
        void loadData();
        void updateData(String username, String avatar_id, String about_me, String gender, String area_id,
                        String province_id, String city_id, String mail, String date);
        void loadPhoto(UploadTokenBean bean, byte[] data);
        void loadToken();
        void loadCity(String country);
    }
}
