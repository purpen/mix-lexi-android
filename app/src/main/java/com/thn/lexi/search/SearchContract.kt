package com.thn.lexi.search

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.ProductBean

class SearchContract {
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
    }

    interface Presenter : BasePresenter {
        fun getUserRecentLook()
    }
}