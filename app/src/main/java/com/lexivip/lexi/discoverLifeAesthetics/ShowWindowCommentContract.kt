package com.lexivip.lexi.discoverLifeAesthetics

import android.support.annotation.NonNull
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.CommentBean

class ShowWindowCommentContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(comments: MutableList<CommentBean>) {

        }

        fun addData(comments: MutableList<CommentBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, view1: ImageView, textViewLikeCount: TextView) {

        }

        fun setPraiseCommentState(doPraise: Boolean, position: Int, isSubAdapter: Boolean) {

        }

        fun loadMoreFail() {

        }

        fun noticeCommentSucess(data: CommentSuccessBean.DataBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(rid:String,isRefresh: Boolean)
        fun loadMoreData(rid: String)
        fun praiseComment(comment_id: String,isPraise:Boolean,position: Int, view1: android.view.View, isSubAdapter: Boolean)
        fun praiseComment(view1: android.view.View, commentBean:CommentBean,adapter:ShowWindowSubCommentListAdapter)
        fun loadMoreSubComments(item:CommentBean, view1: android.view.View, adapter:ShowWindowCommentListAdapter)
        fun submitComment(rid: String, pid: String, replyId: String,content: String, sendButton: Button)
    }
}