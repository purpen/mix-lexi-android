package com.lexivip.lexi.cashMoney;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;

import org.json.JSONArray;
import org.json.JSONException;

public class NameAuthenticationContract {
    interface View extends BaseView<CashAlipayConract.Presenter> {
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setData();
        void setImageId(JSONArray ids) throws JSONException;
        void setToken(UploadTokenBean bean);
    }
    interface Presenter extends BasePresenter {
        void loadData(String name,String id_card,int id_card_front,int id_card_back);
        void loadPhoto(UploadTokenBean bean, byte[] data);
    }
}
