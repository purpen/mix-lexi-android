package com.lexivip.lexi.brandHouse;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.beans.ProductBean;

import java.util.List;

public class BrandHouseGoodsContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void loadMoreFail();
        void loadMoreEnd();
        void loadMoreComplete();
        void setGoodsData(int count);
        void addData(List<ProductBean> data);
        void setNewData(List<ProductBean> data);
        void showNull();
    }
    interface Presenter extends BasePresenter{
        void loadGoodsData(String rid,int page,String cid,String min_price,String max_price,String sort_type,String sort_newest);
        void loadGoodsClassify(String sid,IDataSource.HttpRequestCallBack callBack);
    }
}
