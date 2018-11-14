package com.lexivip.lexi.publishShopWindow
import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.search.FuzzyWordMatchListBean
import com.lexivip.lexi.search.HotSearchBean
import com.lexivip.lexi.search.SearchHotRecommendPavilionBean

class ShopWindowTagsContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setNewData(data: List<ProductBean>) {
        }

        fun addData(products: MutableList<ProductBean>) {

        }

        fun setRecentLookData(products: List<ProductBean>) {

        }

        fun setHotRecommendPavilionData(hot_recommends: MutableList<SearchHotRecommendPavilionBean.DataBean.HotRecommendsBean>) {

        }

        fun setHotSearchData(search_items: List<HotSearchBean.DataBean.SearchItemsBean>) {

        }

        fun setFuzzyWordListData(search_items: List<FuzzyWordMatchListBean.DataBean.SearchItemsBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun getUserRecentLook()
        fun getHotRecommendPavilion()
        fun getHotSearch()
        fun getFuzzyWordList(keyWord: String)
    }
}