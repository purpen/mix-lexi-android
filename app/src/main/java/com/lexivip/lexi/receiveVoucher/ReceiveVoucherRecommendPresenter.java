package com.lexivip.lexi.receiveVoucher;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReceiveVoucherRecommendPresenter implements ReceiveVoucherRecommendContract.Presenter {
    private ReceiveVoucherRecommendModel model=new ReceiveVoucherRecommendModel();
    private ReceiveVoucherRecommendContract.View view;

    public ReceiveVoucherRecommendPresenter(ReceiveVoucherRecommendContract.View view) {
        this.view = view;
    }

    @Override
    public void loadBrand(String store_category, String page) {

    }

    @Override
    public void loadGoods(String store_category, String rid) {

    }

    @Override
    public void loadImage() {

    }

    @Override
    public void loadOfficial() {
        model.loadOfficical(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                VoucherOfficialBean bean=JsonUtil.fromJson(json,VoucherOfficialBean.class);

            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
