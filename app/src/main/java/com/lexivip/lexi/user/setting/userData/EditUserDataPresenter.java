package com.lexivip.lexi.user.setting.userData;

import android.graphics.Bitmap;

import com.basemodule.tools.Constants;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.SPUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.address.CityBean;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;
import com.lexivip.lexi.user.login.UserProfileBean;
import com.qiniu.android.storage.UpProgressHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditUserDataPresenter implements EditUserDataContract.Presenter{
    private EditUserDataModel model=new EditUserDataModel();
    private EditUserDataContract.View view;
    private HashMap<String, ArrayList<CityBean.CityNameBean>> cityMap;

    public EditUserDataPresenter(EditUserDataContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        model.loadData(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("个人中心："+json);
                UserProfileBean bean=JsonUtil.fromJson(json,UserProfileBean.class);
                if (bean.success){
                    view.getData(bean);
                }else {
                    UserProfileBean beans=JsonUtil.fromJson(SPUtil.read(Constants.USER_PROFILE),UserProfileBean.class);
                    view.getData(beans);
                    view.showError(bean.status.message);
                }
                view.dismissLoadingView();
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void updateData(String username, String avatar_id, String about_me, String gender, String area_id, String province_id, String city_id, String mail, String date) {
        model.updateData(username, avatar_id, about_me, gender, area_id, province_id, city_id, mail, date, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                UserProfileBean bean=JsonUtil.fromJson(json,UserProfileBean.class);
                if (bean.success){
                    ToastUtil.showError("保存成功！");
                    view.getUpdate();
                }else {
                    view.showError(bean.status.message);
                }
                view.dismissLoadingView();
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadPhoto(UploadTokenBean bean, byte[] data) {
        view.showLoadingView();
        model.uploadImage(bean, data, new IDataSource.UpLoadCallBack() {
            @Override
            public void onComplete(JSONArray ids) {
                view.dismissLoadingView();
                if (ids==null){
                    ToastUtil.showError("上传图片失败！");
                }else {
                    view.getImage(ids);
                }
            }
        }, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {

                if (percent==1){
                    view.dismissLoadingView();
                }
            }
        });
    }

    @Override
    public void loadToken() {
        model.loadToken(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                UploadTokenBean bean=JsonUtil.fromJson(json,UploadTokenBean.class);
                if (bean.success){
                    view.setToken(bean);
                }else {
                    view.showError(bean.status.message);
                }
                view.dismissLoadingView();
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadCity(String country) {
        if (cityMap!=null&&cityMap.isEmpty()){
            view.getCity(cityMap);
        }else {
            model.loadCity(country, new IDataSource.HttpRequestCallBack() {
                @Override
                public void onSuccess(@NotNull Bitmap json) {

                }

                @Override
                public void onStart() {
                    view.showLoadingView();
                }

                @Override
                public void onSuccess(@NotNull String json) {
                    CityBean bean = JsonUtil.fromJson(json, CityBean.class);
                    if (bean.isSuccess()) {
                        HashMap<String, ArrayList<CityBean.CityNameBean>> map = bean.getData();
                        cityMap = map;
                        view.getCity(map);
                        view.dismissLoadingView();
                    } else {
                        view.dismissLoadingView();
                        view.showError(bean.getStatus().getMessage());
                    }
                }

                @Override
                public void onFailure(@NotNull IOException e) {
                    view.showError(Util.getString(R.string.text_net_error));
                }
            });
        }
    }
}
