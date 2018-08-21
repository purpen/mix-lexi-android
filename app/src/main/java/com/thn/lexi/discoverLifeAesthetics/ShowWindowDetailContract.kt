package com.thn.lexi.discoverLifeAesthetics

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.explore.EditorRecommendBean
import com.thn.lexi.index.selection.DiscoverLifeBean

class ShowWindowDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(shopWindows: MutableList<ShowWindowBean.DataBean.ShopWindowsBean>) {

        }

        fun addData(shopWindows: MutableList<ShowWindowBean.DataBean.ShopWindowsBean>) {

        }


        fun setFavorite(b: Boolean) {

        }

        fun setGuessLikeData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {

        }

        fun setRelateShowWindowData(shop_windows: List<DiscoverLifeBean.DataBean.ShopWindowsBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh:Boolean)
        fun getGuessLike()
    }
}