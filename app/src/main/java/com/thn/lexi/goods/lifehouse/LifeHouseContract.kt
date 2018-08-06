package com.thn.lexi.goods.lifehouse

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.goods.selection.GoodsData

class LifeHouseContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setNewData(data: List<DistributionGoodsBean.DataBean.ProductsBean>) {

        }

        fun addData(products: List<DistributionGoodsBean.DataBean.ProductsBean>) {

        }

        fun loadMoreEnd(){}
        fun loadMoreComplete() {

        }


    }

    interface Presenter : BasePresenter {
        fun loadData(cid: String, page: Int)

        fun loadMoreData(cid: String, page: Int)
    }
}