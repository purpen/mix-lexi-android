package com.lexivip.lexi.shopCart

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.index.detail.AddShopCartBean

class ShopCartContract {

    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setShopCartGoodsData(data: ShopCartBean.DataBean) {

        }

        fun setNewData(products: MutableList<ProductBean>) {

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

        fun setAddWishOrderStatus(list: ArrayList<String>) {

        }

        fun removeShopCartSuccess() {

        }

        fun updateShopCart() {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh: Boolean)
        fun loadMoreData()
        fun getShopCartGoods()
        fun getGoodsSKUs(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack)
        fun updateReselectSKU(newSKU: String, oldSKU: String, quantity: Int)
        fun addShopCart(rid: String, quantity: Int)
        fun addWishOrder(list: ArrayList<String>)
        fun removeProductFromShopCart(list: ArrayList<String>)
    }
}