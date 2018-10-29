package com.lexivip.lexi.discoverLifeAesthetics

import android.support.annotation.NonNull
import android.view.View
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean

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

        fun setRelateShowWindowData(windows: List<ShopWindowBean>) {

        }

        fun setUserFocusState(b: Boolean) {

        }

        fun setShowWindowData(data: ShowWindowDetailBean.DataBean?) {

        }

        fun setCommentState() {

        }

        fun setPraiseCommentState(b: Boolean, position: Int, isSubAdapter: Boolean) {

        }
    }

    interface Presenter : BasePresenter {
        fun focusUser(uid: String, view1: android.view.View)
        fun unfocusUser(uid: String, view1: android.view.View)
        fun loadData(rid:String,isRefresh:Boolean)
        fun getGuessLike(rid: String)
        fun sendComment(rid: String, pid: String, content: String)
        fun praiseComment(comment_id: String, isPraise:Boolean, position: Int, view1: android.view.View, isSubAdapter: Boolean)
        fun loadMoreSubComments(comment_id: String, position: Int, view1: android.view.View)
    }
}