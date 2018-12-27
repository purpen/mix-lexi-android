package com.lexivip.lexi.discoverLifeAesthetics

import android.support.annotation.NonNull
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean

class ShowWindowDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setFavorite(b: Boolean, view1: ImageView, textViewLikeCount: TextView) {

        }

        fun setGuessLikeData(products: List<ProductBean>) {

        }

        fun setRelateShowWindowData(windows: List<ShopWindowBean>) {

        }

        fun setUserFocusState(b: Boolean) {

        }

        fun setShowWindowData(data: ShowWindowDetailBean.DataBean?) {

        }

        fun setPraiseCommentState(b: Boolean, position: Int, isSubAdapter: Boolean) {

        }

        fun noticeCommentSuccess(data: CommentSuccessBean.DataBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun focusUser(uid: String, view1: android.view.View)
        fun unfocusUser(uid: String, view1: android.view.View)
        fun loadData(rid:String,isRefresh:Boolean)
        fun getGuessLike(rid: String)
        fun praiseComment(comment_id: String, isPraise:Boolean, position: Int, view1: android.view.View, isSubAdapter: Boolean)
        fun loadMoreSubComments(item: CommentBean, view1: android.view.View, adapter: ShopWindowDetailCommentListAdapter)
        fun submitComment(rid: String, pid: String,replyId:String, content: String, sendButton: Button)
    }
}