package com.lexivip.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CloseHousePresenter implements CloseHouseContract.Presenter{
    private CloseHouseModel model=new CloseHouseModel();
    private CloseHouseContract.View view;

    public CloseHousePresenter(CloseHouseContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(String rid) {
        model.loadData(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                BrandHouseNoticeBean bean=JsonUtil.fromJson(json,BrandHouseNoticeBean.class);
                if (bean.success){
                    view.setData(bean);
                    view.dismissLoadingView();
                }else{
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
