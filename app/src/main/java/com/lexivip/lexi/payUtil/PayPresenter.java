package com.lexivip.lexi.payUtil;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PayPresenter implements PayContract.Presenter{
    private PayModel model=new PayModel();
    private PayContract.View view;

    public PayPresenter(PayContract.View view) {
        this.view = view;
    }

    @Override
    public void loadWXPayOrder(String rid, int pay_type, int type) {
        model.loadWXPayOrder(rid, pay_type, type, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                PayWXBean bean=JsonUtil.fromJson(json,PayWXBean.class);
                if (bean.success){
                    view.getPayOrder(bean);
                    view.dismissLoadingView();
                }else {
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
