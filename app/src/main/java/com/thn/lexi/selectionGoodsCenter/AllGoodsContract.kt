package com.thn.lexi.selectionGoodsCenter

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class AllGoodsContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(products: MutableList<HotGoodsBean.DataBean.ProductsBean>) {

        }

        fun addData(products: List<HotGoodsBean.DataBean.ProductsBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(page: Int, sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String)
    }
}