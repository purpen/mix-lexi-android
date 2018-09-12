package com.thn.lexi.shopCart

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.basemodule.ui.IDataSource
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.AddShopCartBean

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

        fun setAddShopCartSuccess(cartBean: AddShopCartBean.DataBean.CartBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData()
        fun loadMoreData()
        fun getShopCartGoods()
        fun getGoodsSKUs(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack)
        fun addShopCart(rid: String, quantity: Int)
    }
}