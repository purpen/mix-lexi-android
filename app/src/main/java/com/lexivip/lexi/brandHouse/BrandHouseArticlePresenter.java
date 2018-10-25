package com.lexivip.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BrandHouseArticlePresenter implements BrandHouseArticleContract.Presenter {
    private BrandHouseArticleModel model=new BrandHouseArticleModel();
    private BrandHouseArticleContract.View view;

    public BrandHouseArticlePresenter(BrandHouseArticleContract.View view) {
        this.view = view;
    }

    @Override
    public void loadArticle(String rid, final String page) {
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
                    if (page.equals("1")) {
                        if (bean.data.life_records.isEmpty()){
                            view.showNull();
                        }else {
                            view.setArticle(bean);
                            view.loadMoreComplete();
                        }
                    }else {
                        if (bean.data.life_records.isEmpty()){
                            view.loadMoreEnd();
                        }else {
                            view.addArticle(bean);
                            view.loadMoreComplete();
                        }
                    }
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
}
