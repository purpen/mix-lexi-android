package com.thn.lexi.brandPavilion

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.discoverLifeAesthetics.ShowWindowBean

class FeatrueBrandPavilionContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(shopWindows: MutableList<ShowWindowBean.DataBean.ShopWindowsBean>) {

        }

        fun addData(shopWindows: MutableList<ShowWindowBean.DataBean.ShopWindowsBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh:Boolean)
        fun loadMoreData()
    }
}