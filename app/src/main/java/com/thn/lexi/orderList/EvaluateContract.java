package com.thn.lexi.orderList;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.thn.lexi.user.completeinfo.UploadTokenBean;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class EvaluateContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setToken(UploadTokenBean bean);
        void setImageId(JSONArray ids) throws JSONException;
        void finishActivity();
    }
    interface Presenter extends BasePresenter{
        void loadImage(UploadTokenBean bean,byte[] data);
        void getToken();
        void setEvaluate(String order_rid, ArrayList<EvaluateBean> items);
    }
}
