package com.lexivip.lexi.index.explore.collection

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.index.explore.GoodsCollectionBean

class CollectionListContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(collections: MutableList<GoodsCollectionBean.DataBean.CollectionsBean>) {

        }

        fun addData(collections: MutableList<GoodsCollectionBean.DataBean.CollectionsBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun loadMoreFail() {

        }

    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh:Boolean)
        fun loadMoreData()
    }
}