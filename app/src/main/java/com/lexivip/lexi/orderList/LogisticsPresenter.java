package com.lexivip.lexi.orderList;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.lexivip.lexi.JsonUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LogisticsPresenter implements LogisticsContract.Presenter {
    private LogisticsModel model=new LogisticsModel();
    private LogisticsContract.View view;

    public LogisticsPresenter(LogisticsContract.View view) {
        this.view = view;
    }

    @Override
    public void getData(String logistic_code, String kdn_company_code) {
        model.getData(logistic_code, kdn_company_code, new IDataSource.HttpRequestCallBack() {
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
                LogisticsBean bean= JsonUtil.fromJson(json,LogisticsBean.class);
                if (bean.isSuccess()){
                    if (bean.getData().getTraces()!=null&&!bean.getData().getTraces().isEmpty()) {
                        view.showList(bean.getData());
                    }else {
                        view.shoeNull();
                    }
                }else{
                    view.showError(bean.getStatus().getMessage());
                    view.shoeNull();
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }
}
