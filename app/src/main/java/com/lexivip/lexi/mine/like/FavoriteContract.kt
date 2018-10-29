package com.lexivip.lexi.mine.like

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.index.selection.DiscoverLifeBean

class FavoriteContract {

    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()

        fun setGoodsLikeData(products: List<ProductBean>) {

        }

        fun setShowWindowData(shop_windows: List<ShopWindowBean>) {

        }

    }

    interface Presenter : BasePresenter {
        fun getUserGoodsLike()
        fun getShowWindowLike()
    }
}