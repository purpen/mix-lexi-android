package com.thn.lexi.brandPavilion

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.explore.BrandPavilionListBean

class FeatrueBrandPavilionContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(stores: MutableList<BrandPavilionListBean.DataBean.StoresBean>) {

        }

        fun addData(stores: MutableList<BrandPavilionListBean.DataBean.StoresBean>) {

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