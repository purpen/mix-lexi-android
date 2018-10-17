package com.lexivip.lexi.brandHouse;

import android.support.annotation.NonNull;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.beans.ProductBean;
import com.lexivip.lexi.index.detail.ShopCouponListBean;

import java.util.List;

public class BrandHouseContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(@NonNull String error);
        void setData(BrandHouseBean bean);
        void setNoticeData(BrandHouseNoticeBean bean);
        void setCouponsData(ShopCouponListBean bean);
        void setGoodsData(int count);
        void loadMoreFail();
        void loadMoreEnd();
        void loadMoreComplete();
        void addData(List<ProductBean> data);
        void setNewData(List<ProductBean> data);
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
