package com.lexivip.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.index.detail.GetCouponBean;
import com.lexivip.lexi.index.detail.ShopCouponListBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BrandHousePresenter implements BrandHouseContract.Presenter {
    private BrandHouseModel model=new BrandHouseModel();
    private BrandHouseContract.View view;
    private String sortType="1";
    private int pages=1;

    public BrandHousePresenter(BrandHouseContract.View view) {
        this.view = view;
    }

    public String getSortType() {
        return sortType;
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
                BrandHouseBean bean=JsonUtil.fromJson(json,BrandHouseBean.class);
                if (bean.success){
                    view.setData(bean);
                }else{
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadNoticeData(String rid) {
        model.loadNoticeData(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("公告："+json);
                BrandHouseNoticeBean bean=JsonUtil.fromJson(json,BrandHouseNoticeBean.class);
                if (bean.success){
                    view.setNoticeData(bean);
                }else{
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadCouponsData(String rid) {
        model.loadCouponsData(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("优惠劵"+json);
                ShopCouponListBean bean=JsonUtil.fromJson(json,ShopCouponListBean.class);
                if (bean.success){
                    view.setCouponsData(bean);
                }else{
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadGoodsData(String rid, final int page, String cid, String min_price, String max_price, String sort_type, String sort_newest) {
        if (sort_type!=null) {
            sortType=sort_type;
        }
        if (page!=0){
            this.pages=page;
        }
        model.loadGoodsData(rid, String.valueOf(this.pages), cid, min_price, max_price, sortType, sort_newest, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("商品："+json);
                view.dismissLoadingView();
                BrandHouseGoodsBean bean=JsonUtil.fromJson(json,BrandHouseGoodsBean.class);
                if (bean.success) {
                    view.setGoodsData(bean.data.count);
                    if (page==1){
                        view.setNewData(bean.data.products);
                        view.loadMoreComplete();
                    }else{
                        if(bean.data.products.isEmpty()){
                            view.loadMoreEnd();
                        }else {
                            view.addData(bean.data.products);
                            view.loadMoreComplete();
                        }
                    }
                    pages++;
                }else {
                    view.loadMoreFail();
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadGoodsClassify(String sid, final IDataSource.HttpRequestCallBack callBack) {
        model.loadGoodsClassify(sid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NotNull String json) {
                callBack.onSuccess(json);
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void followStore(String rid) {
        model.followStore(rid, new IDataSource.HttpRequestCallBack() {
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
                BrandHouseFollowBean bean=JsonUtil.fromJson(json,BrandHouseFollowBean.class);
                if (bean.success){
                    view.setIsFollow(bean);
                }else {
                    view.showError(bean.status.message);
                }

            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void unFollowStore(String rid) {
        model.unFollowStore(rid, new IDataSource.HttpRequestCallBack() {
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
                BrandHouseFollowBean bean=JsonUtil.fromJson(json,BrandHouseFollowBean.class);
                if (bean.success){
                    view.setIsFollow(bean);
                }else {
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadArticle(String rid, String page) {
        model.loadArticel(rid, page, new IDataSource.HttpRequestCallBack() {
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
                BrandHouseArticelBean bean=JsonUtil.fromJson(json,BrandHouseArticelBean.class);
                if (bean.success){
                    view.setArticle(bean);
                }else {
                    view.loadMoreFail();
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    public void clickGetCoupon(String storeId, String code, final IDataSource.HttpRequestCallBack callBack){
        model.clickGetCoupon(storeId, code, new IDataSource.HttpRequestCallBack() {
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
                GetCouponBean bean=JsonUtil.fromJson(json,GetCouponBean.class);
                if (bean.success){
                    callBack.onSuccess(json);
                    ToastUtil.showInfo(R.string.text_get_coupon_success);
                }else{
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                callBack.onFailure(e);
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }
}
