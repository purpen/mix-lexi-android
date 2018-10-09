package com.thn.lexi.index.explore.collection

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class CollectionDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(data: CollectionDetailBean.DataBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(id:String,isRefresh:Boolean)
    }
}