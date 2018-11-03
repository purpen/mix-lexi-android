package com.lexivip.lexi.receiveVoucher;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.login.UserProfileUtil;

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
        model.loadBrandRecommend(store_category, page, new IDataSource.HttpRequestCallBack() {
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
    public void loadGoods(String store_category, String rid,String page) {
        model.loadGoods(store_category, rid,page, new IDataSource.HttpRequestCallBack() {
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
                    if(bean.data.coupons.size()==10){
                        view.loadMoreComplete();
                    }else {
                        view.loadMoreEnd();
                    }
                    view.dismissLoadingView();
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

    @Override
    public void loadImage() {
        model.loadImage(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("图片："+json);
                //VoucherNoticeBean bean=JsonUtil.fromJson(json,VoucherNoticeBean.class);
                /*if (bean.success){
                    //view.im
                }else {
                    view.showError(bean.status.message);
                }*/
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
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
                LogUtil.e("官方券："+json);
                VoucherOfficialBean bean=JsonUtil.fromJson(json,VoucherOfficialBean.class);
                if (bean.success){
                    view.getOfficial(bean);
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
    public void receiveVoucher(String rid, String store_rid) {
        model.receiveVoucher(rid, store_rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                VoucherReceiveGoodsBean bean=JsonUtil.fromJson(json,VoucherReceiveGoodsBean.class);
                if (bean.success){
                    view.getReceive(true);
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
    public void receiveOfficial(String rid) {
        model.receiveOfficial(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("领取："+json);
                VoucherReceiveOfficialBean bean=JsonUtil.fromJson(json,VoucherReceiveOfficialBean.class);
                if (bean.success){
                    view.getReceiveOfficial(true);
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
