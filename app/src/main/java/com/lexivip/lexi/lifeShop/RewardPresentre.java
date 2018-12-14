package com.lexivip.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RewardPresentre implements RewardContract.Presenter {
    private RewardContract.View view;
    private RewardModel model=new RewardModel();

    public RewardPresentre(RewardContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int page) {
        model.loadData(page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                RewardBean bean=JsonUtil.fromJson(json,RewardBean.class);
                if (bean.success){
                    if (bean.data.rewards.isEmpty()){
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
