package com.lexivip.lexi.cashMoney;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.lifeShop.MyFriendBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CashRecordPresenter implements CashRecordContract.Presenter{
    private CashRecordContract.View view;
    private CashRecordModel model=new CashRecordModel();

    public CashRecordPresenter(CashRecordContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int type) {
        model.loadData(type, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                CashRecordBean bean=JsonUtil.fromJson(json,CashRecordBean.class);
                if (bean.success){
                    if (bean.data.record_list.isEmpty()){
                        view.loadMoreEnd();
                    }else {
                        view.loadMoreComplete();
                        view.dismissLoadingView();
                        view.setData(bean);
                    }
                }else {
                    view.loadMoreFail();
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
