package com.lexivip.lexi.index.discover

import android.support.annotation.NonNull
import android.view.View
import android.widget.Button
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.beans.LifeWillBean
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.discoverLifeAesthetics.CommentSuccessBean
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowCommentListBean
import org.json.JSONObject


class ArticleDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setData(data: JSONObject) {

        }

        fun setFocusState(followed_status: Int) {

        }

        fun setRelateStoriesData(data: MutableList<LifeWillBean>) {

        }

        fun setRecommendProductsData(products: List<ProductBean>) {

        }

        fun setBrandPavilionFocusState(favorite: Boolean) {

        }

        fun setHeadPavilionFocusState(favorite: Boolean) {

        }

        fun setPraiseCommentState(b: Boolean, position: Int, subAdapter: Boolean) {

        }

        fun setCommentListData(data: ShowWindowCommentListBean.DataBean) {

        }

        fun noticeCommentSuccess(data: CommentSuccessBean.DataBean) {

        }

        fun praiseArticleSuccess(b: Boolean) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun addData(comments: List<CommentBean>) {

        }

        fun loadMoreFail() {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh: Boolean, rid: String)
        fun focusUser(uid: String, v: android.view.View, isFollow: Boolean)
        fun getRelateStories(rid: String)
        fun getRecommendProducts(rid: String)
        fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, v: android.view.View,isHeaderPavilion:Boolean)

        fun getArticleComments(rid: String)

        fun getArticleComments(rid: String,isRefresh: Boolean)
        /**
         * 加载更多评论
         */
        fun loadMoreArticleComments()

        /**
         * 评论点赞
         */
        fun praiseComment(view1: android.view.View, commentBean: CommentBean, adapter: ArticleSubCommentListAdapter)
        fun praiseComment(comment_id: String, isPraise: Boolean, position: Int, view1: android.view.View, isSubAdapter: Boolean)

        /**
         * 提交评论
         */
        fun submitComment(rid: String, pid: String,replyId:String, content: String, sendButton: Button)

        /**
         * 文章点赞
         */
        fun praiseArticle(rid: String,isPraise: Boolean, view1: android.view.View)
    }
}