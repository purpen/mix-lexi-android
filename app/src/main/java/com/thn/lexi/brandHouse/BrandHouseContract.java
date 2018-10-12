package com.thn.lexi.brandHouse;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.basemodule.ui.IDataSource;
import com.thn.lexi.beans.ProductBean;
import com.thn.lexi.index.detail.ShopCouponListBean;

import java.util.ArrayList;

public class BrandHouseContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void setData(BrandHouseBean bean);
        void setNoticeData(BrandHouseNoticeBean bean);
        void setCouponsData(ShopCouponListBean bean);
        void setGoodsData(BrandHouseGoodsBean bean);
        void loadMoreFail();
        void loadMoreEnd();
        void loadMoreComplete();
        void addData(ArrayList<ProductBean> products);
        void setNewData(ArrayList<ProductBean> data);
        void setIsFollow(BrandHouseFollowBean bean);
        void setArticle(BrandHouseArticelBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid);
        void loadNoticeData(String rid);
        void loadCouponsData(String rid);
        void loadGoodsData(String rid,int page,String cid,String min_price,String max_price,String sort_type,String sort_newest);
        void loadGoodsClassify(String sid,IDataSource.HttpRequestCallBack callBack);
        void followStore(String rid);
        void unFollowStore(String rid);
        void loadArticle(String rid,String page);
    }
}
