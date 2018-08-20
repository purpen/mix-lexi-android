package com.thn.lexi.discoverLifeAesthetics

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class ShowWindowContract {
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
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh:Boolean)
        fun loadMoreData()
    }
}