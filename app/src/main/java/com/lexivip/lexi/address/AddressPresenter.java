package com.lexivip.lexi.address;

import android.graphics.Bitmap;
import android.util.Log;

import com.lexivip.lexi.JsonUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.areacode.CountryAreaCodeBean;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;
import com.qiniu.android.storage.UpProgressHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AddressPresenter implements AddressContract.Presenter {
    private AddressContract.View view;
    private AddressModel model=new AddressModel();
    public AddressPresenter(AddressContract.View view) {
        this.view=view;
    }

    @Override
    public void loadData(String rid) {
        model.getData(rid,new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e(json);
                view.dismissLoadingView();
                AddressBean bean= JsonUtil.fromJson(json,AddressBean.class);
                view.setAddressData(bean.getData());
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadCityData(String country) {
        model.getCityData(country, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                 LogUtil.e("数据形式："+json);
                view.dismissLoadingView();
                HashMap<String,ArrayList<CityBean.CityNameBean>> map=JsonUtil.fromJson(json,CityBean.class).getData();
                view.setCityData(map);
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadPhoto(UploadTokenBean bean, byte[] data) {
        view.showLoadingView();
        if (bean==null){
            ToastUtil.showInfo(R.string.text_net_error);
            return;
        }
        model.uploadPhoto(data, bean, new IDataSource.UpLoadCallBack() {
            @Override
            public void onComplete(JSONArray ids) {
                view.dismissLoadingView();
                if (ids==null){
                    ToastUtil.showError("上传图片失败！");
                }else {
                    try {
                        view.setImageId(ids);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtil.showError("上传图片失败！");
                    }
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
    public void loadCountry() {
        model.getCountry(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                view.dismissLoadingView();
                CountryAreaCodeBean bean=JsonUtil.fromJson(json,CountryAreaCodeBean.class);
                view.setCountry(bean);
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void deleteAddress(String rid) {
        model.deleteAddress(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e(json);
                view.dismissLoadingView();
                AddressBean bean= JsonUtil.fromJson(json,AddressBean.class);
                if (bean.isSuccess()){
                    view.finishActivity(bean.getData(),true);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void saveAddress(AddressBean.DataBean bean,String rid, boolean is_overseas, String id_card, String id_card_front, String id_card_back,int type) {
        model.saveAddress(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("返回的："+json);
                AddressBean bean= JsonUtil.fromJson(json,AddressBean.class);
                if (bean.isSuccess()){
                    view.finishActivity(bean.getData(),false);
                    view.dismissLoadingView();
                }else {
                    view.dismissLoadingView();
                    view.showError(bean.getStatus().getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        },bean, rid,is_overseas,id_card,id_card_front,id_card_back,type);
    }

    @Override
    public void loadForeign(String user_name, String mobile) {

    }

    public void getToken(){
        model.getToken(new IDataSource.HttpRequestCallBack() {
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
                }else{
                    view.showError(bean.status.message);
                }
                view.dismissLoadingView();
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

}
