package com.lexivip.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BrandHouseGoodsPresenter implements BrandHouseGoodsContract.Presenter {


    //综合排序
    public static final String SORT_TYPE_SYNTHESISE = "1";

    //价格由低到高
    public static final String SORT_TYPE_LOW_UP = "2";

    //价格由高到低
    public static final String SORT_TYPE_UP_LOW = "3";

    //默认综合排序
    private String sortType = SORT_TYPE_SYNTHESISE;

    private int pages = 1;
    private BrandHouseGoodsModel model = new BrandHouseGoodsModel();
    private BrandHouseGoodsContract.View view;
    private String minePrice = "";
    private String maxPrice = "";
    private String cids = "";

    public BrandHouseGoodsPresenter(BrandHouseGoodsContract.View view) {
        this.view = view;
    }

    public String getSortType() {
        return sortType;
    }

    public String getMinePrice() {
        return minePrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public String getCids() {
        return cids;
    }

    @Override
    public void loadGoodsData(String rid, final int page, String cid, String min_price, String max_price, String sort_type, String sort_newest) {
        this.maxPrice = max_price;
        this.minePrice = min_price;
        this.sortType = sort_type;
        this.cids = cid;
        if (page != 0) {
            this.pages = page;
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
                LogUtil.e("商品：" + json);
                view.dismissLoadingView();
                BrandHouseGoodsBean bean = JsonUtil.fromJson(json, BrandHouseGoodsBean.class);
                if (bean.success) {
                    view.setGoodsData(bean.data.count);
                    if (page == 1) {
                        if (bean.data.products.isEmpty()) {
                            view.showNull();
                        } else {
                            view.setNewData(bean.data.products);
                            view.loadMoreComplete();
                        }
                    } else {
                        if (bean.data.products.isEmpty()) {
                            view.loadMoreEnd();
                        } else {
                            view.addData(bean.data.products);
                            view.loadMoreComplete();
                        }
                    }
                    pages++;
                } else {
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
