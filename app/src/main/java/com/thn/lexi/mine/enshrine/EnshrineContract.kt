package com.thn.lexi.mine.enshrine

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.bean.ProductBean

class EnshrineContract {

    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()

        fun setRecentLookData(products: List<ProductBean>) {

        }

        fun setWishOrderData(products: List<ProductBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun getUserRecentLook()
        fun getWishOrder()
    }
}