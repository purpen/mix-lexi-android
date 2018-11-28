package com.lexivip.lexi.dialog;

import android.graphics.Bitmap;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.BaseView;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CouponPresenter implements CouponContract.Presenter{
    CouponModel model=new CouponModel();
    private CouponContract.View view;

    public CouponPresenter(CouponContract.View view) {
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

            }

            @Override
            public void onSuccess(@NotNull String json) {
                CouponBean bean=JsonUtil.fromJson(json,CouponBean.class);
                if (bean.success){
                    view.loadData();
                }else {
                    ToastUtil.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                ToastUtil.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
