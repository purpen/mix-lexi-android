package com.lexivip.lexi.index.explore.collection

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean

class CollectionDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(data: CollectionDetailBean.DataBean) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {
            
        }

        fun addData(products: List<ProductBean>) {
        }

        fun loadMoreFail() {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(id:String,isRefresh:Boolean)
    }
}