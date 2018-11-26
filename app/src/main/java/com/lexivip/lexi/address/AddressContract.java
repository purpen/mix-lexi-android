package com.lexivip.lexi.address;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.lexivip.lexi.user.areacode.CountryAreaCodeBean;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class AddressContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setAddressData(AddressBean.DataBean data);
        void setCityData(HashMap<String,ArrayList<CityBean.CityNameBean>> map);
        void setToken(UploadTokenBean bean);
        void setImageId(JSONArray ids) throws JSONException;
        void setCountry(CountryAreaCodeBean bean);
        void finishActivity();
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid);
        void loadCityData(String country);
        void loadPhoto(UploadTokenBean bean,byte[] data);
        void loadCountry();
        void deleteAddress(String rid);
        void saveAddress(AddressBean.DataBean bean,String rid, boolean is_overseas, String id_card, String id_card_front, String id_card_back,int type);
    }

}
