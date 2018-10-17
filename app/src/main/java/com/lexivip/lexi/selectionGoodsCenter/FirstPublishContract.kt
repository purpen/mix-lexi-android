package com.lexivip.lexi.selectionGoodsCenter

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean

class FirstPublishContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(products: MutableList<ProductBean>) {

        }

        fun addData(products: List<ProductBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData()
    }
}