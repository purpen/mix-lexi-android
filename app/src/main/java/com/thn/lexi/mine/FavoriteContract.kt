package com.thn.lexi.mine

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.bean.ProductBean
import com.thn.lexi.index.selection.DiscoverLifeBean
import com.thn.lexi.index.selection.GoodsData

class FavoriteContract {

    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()

        fun setGoodsLikeData(products: List<ProductBean>) {

        }

        fun setShowWindowData(shop_windows: List<DiscoverLifeBean.DataBean.ShopWindowsBean>) {

        }

    }

    interface Presenter : BasePresenter {
        fun getUserGoodsLike()
        fun getShowWindowLike()
    }
}