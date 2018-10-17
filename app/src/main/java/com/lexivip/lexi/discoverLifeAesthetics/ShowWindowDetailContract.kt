package com.lexivip.lexi.discoverLifeAesthetics

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.index.selection.DiscoverLifeBean

class ShowWindowDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setFavorite(b: Boolean) {

        }

        fun setGuessLikeData(products: List<ProductBean>) {

        }

        fun setRelateShowWindowData(shop_windows: List<DiscoverLifeBean.DataBean.ShopWindowsBean>) {

        }

        fun setUserFocusState(b: Boolean) {

        }

        fun setShowWindowData(data: ShowWindowDetailBean.DataBean?) {

        }

        fun setCommentState() {

        }
    }

    interface Presenter : BasePresenter {
        fun focusUser(uid:String,view1: android.view.View)
        fun unfocusUser(uid:String,view1: android.view.View)
        fun loadData(rid:String,isRefresh:Boolean)
        fun getGuessLike(rid: String)
        fun sendComment(rid: String, pid: String, content: String)
    }
}