package com.thn.lexi.search

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.ProductBean

class SearchGoodsContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setNewData(data: List<ProductBean>) {
        }

        fun addData(products: MutableList<ProductBean>) {

        }

        fun loadMoreEnd() {}
        fun loadMoreComplete() {}
        fun loadMoreFail() {}
    }

    interface Presenter : BasePresenter {

        fun loadData(page: Int, sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String)
        fun loadMoreData()
        fun loadData(isRefresh: Boolean, searchString: String)
    }
}