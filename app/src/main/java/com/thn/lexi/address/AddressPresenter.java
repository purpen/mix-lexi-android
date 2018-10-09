package com.thn.lexi.address;

import android.graphics.Bitmap;

import com.thn.lexi.JsonUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.ui.IDataSource;
import com.thn.lexi.AppApplication;
import com.thn.lexi.R;
import com.thn.lexi.user.areacode.CountryAreaCodeBean;
import com.thn.lexi.user.completeinfo.UploadTokenBean;

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
                //
                // LogUtil.e("数据形式："+json);
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
        if (bean==null){
            ToastUtil.showInfo(R.string.text_net_error);
            return;
        }
        model.uploadPhoto(data, bean, new IDataSource.UpLoadCallBack() {
            @Override
            public void onComplete(@NotNull JSONArray ids) {
                try {
                    view.setImageId(ids);
                } catch (JSONException e) {
                    e.printStackTrace();
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
                    view.finishActivity();
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
    public void saveAddress(AddressBean.DataBean bean, boolean is_overseas, String id_card, String id_card_front, String id_card_back) {
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
                AddressBean bean= JsonUtil.fromJson(json,AddressBean.class);
                if ("200".equals(bean.getStatus().getCode())||"201".equals(bean.getStatus().getCode())){
                    view.finishActivity();
                }
                view.dismissLoadingView();
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        },bean,is_overseas,id_card,id_card_front,id_card_back);
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
                    view.showError(AppApplication.getContext().getString(R.string.text_net_error));
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
