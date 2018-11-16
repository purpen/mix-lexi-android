package com.lexivip.lexi.publishShopWindow

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean

class SelectGoodsContract {
    interface View:BaseView<Presenter>{
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

    interface Presenter:BasePresenter{
        fun loadData(whichPage:Int)
        fun loadMoreData()
    }
}