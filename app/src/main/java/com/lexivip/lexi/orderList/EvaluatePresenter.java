package com.lexivip.lexi.orderList;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.qiniu.android.storage.UpProgressHandler;
import com.lexivip.lexi.JsonUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class EvaluatePresenter implements EvaluateContract.Presenter {
    EvaluateModel model=new EvaluateModel();
    private EvaluateContract.View view;

    public EvaluatePresenter(EvaluateContract.View view) {
        this.view = view;
    }

    @Override
    public void loadImage(UploadTokenBean bean, byte[] data) {
        model.uploadImage(data, bean, new IDataSource.UpLoadCallBack() {
            @Override
            public void onComplete(@NotNull JSONArray ids) {
                try {
                    view.setImageId(ids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                LogUtil.e("key："+key+"   percent："+percent);
            }
        });
    }

    @Override
    public void getToken() {
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
                view.dismissLoadingView();
                UploadTokenBean bean= JsonUtil.fromJson(json,UploadTokenBean.class);
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
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void setEvaluate(String order_rid, ArrayList<EvaluateBean> items) {
        model.setEvaluate(order_rid, items, new IDataSource.HttpRequestCallBack() {
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
                MyOrderListBean myOrderListBean=JsonUtil.fromJson(json,MyOrderListBean.class);
                if (myOrderListBean.isSuccess()){
                    view.finishActivity();
                    ToastUtil.showInfo("评价成功");
                }else{
                    view.showError(Util.getString(R.string.text_net_error));
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
