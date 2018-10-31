package com.lexivip.lexi.receiveVoucher;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReceiveVoucherFragmentPresenter implements ReceiveVoucherFragmentContract.Presenter {
    private ReceiveVoucherRecommendModel model=new ReceiveVoucherRecommendModel();
    private ReceiveVoucherFragmentContract.View view;

    public ReceiveVoucherFragmentPresenter(ReceiveVoucherFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void loadBrand(String store_category, String page) {
        model.loadBrand(store_category, page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("共享券："+json);
                VoucherBrandBean bean=JsonUtil.fromJson(json,VoucherBrandBean.class);
                if (bean.success){
                    view.getBrand(bean);
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

    @Override
    public void loadGoods(String store_category, String rid) {
        model.loadGoods(store_category, rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("商品券："+json);
                VoucherGoodsBean bean=JsonUtil.fromJson(json,VoucherGoodsBean.class);
                if (bean.success){
                    view.getGoods(bean);
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
