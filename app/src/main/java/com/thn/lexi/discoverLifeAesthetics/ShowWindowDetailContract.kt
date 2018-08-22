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

        fun setFavorite(b: Boolean) {

        }

        fun setGuessLikeData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {

        }

        fun setRelateShowWindowData(shop_windows: List<DiscoverLifeBean.DataBean.ShopWindowsBean>) {

        }

        fun setUserFocusState(b: Boolean) {

        }

        fun setShowWindowData(data: ShowWindowDetailBean.DataBean?) {

        }
    }

    interface Presenter : BasePresenter {
        fun focusUser(uid:String,view1: android.view.View)
        fun unfocusUser(uid:String,view1: android.view.View)
        fun loadData(rid:String,isRefresh:Boolean)
        fun getGuessLike()
    }
}