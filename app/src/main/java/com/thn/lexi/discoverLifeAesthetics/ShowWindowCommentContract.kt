package com.thn.lexi.discoverLifeAesthetics

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

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

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun setPraiseCommentState(doPraise: Boolean, position: Int, isSubAdapter: Boolean) {

        }

        fun addSubCommentsData(position: Int,comments: MutableList<CommentBean>){}
    }

    interface Presenter : BasePresenter {
        fun loadData(rid:String,isRefresh: Boolean)
        fun loadMoreData(rid: String)
        fun praiseComment(comment_id: String, position: Int, view1: android.view.View, isSubAdapter: Boolean)
        fun cancelPraiseComment(comment_id: String, position: Int, view1: android.view.View, isSubAdapter: Boolean)
        fun loadMoreSubComments(comment_id: String,position: Int,view1: android.view.View)
    }
}