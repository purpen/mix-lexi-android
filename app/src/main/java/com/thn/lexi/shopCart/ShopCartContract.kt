package com.thn.lexi.shopCart

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.ProductBean

class ShopCartContract {

    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setShopCartGoodsData(data: ShopCartBean.DataBean) {

        }

        fun setNewData(products: List<ProductBean>) {

        }

        fun loadMoreEnd() {
            
        }

        fun loadMoreComplete() {
            
        }

        fun loadMoreFail() {
            
        }

        fun addData(products: List<ProductBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData()
        fun loadMoreData()
        fun getShopCartGoods()
    }
}