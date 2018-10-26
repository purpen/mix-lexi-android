package com.lexivip.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BrandHouseGoodsPresenter implements BrandHouseGoodsContract.Presenter{
    private String sortType="1";
    private int pages=1;
    private BrandHouseGoodsModel model=new BrandHouseGoodsModel();
    private BrandHouseGoodsContract.View view;

    public BrandHouseGoodsPresenter(BrandHouseGoodsContract.View view) {
        this.view = view;
    }

    public String getSortType() {
        return sortType;
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
                        if (bean.data.products.isEmpty()){
                            view.showNull();
                        }else {
                            view.setNewData(bean.data.products);
                            view.loadMoreComplete();
                        }
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
}
