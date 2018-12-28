package com.lexivip.lexi.discoverLifeAesthetics

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import com.lexivip.lexi.index.selection.DiscoverLifeBean
import com.lexivip.lexi.net.NetStatusBean
import java.io.IOException

class ShowWindowDetailPresenter(view: ShowWindowDetailContract.View) : ShowWindowDetailContract.Presenter {
    private var view: ShowWindowDetailContract.View = checkNotNull(view)

    private val dataSource: ShowWindowDetailModel by lazy { ShowWindowDetailModel() }
    var rid:String = ""

    /**
     * 加载详情数据
     */
    override fun loadData(rid: String,isRefresh: Boolean) {
        this.rid = rid
        dataSource.loadData(rid,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e("==========="+json)
                view.dismissLoadingView()
                val showWindowDetailBean = JsonUtil.fromJson(json, ShowWindowDetailBean::class.java)
                if (showWindowDetailBean.success) {
                    view.setShowWindowData(showWindowDetailBean.data)
                } else {
                    view.showError(showWindowDetailBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 喜欢橱窗
     */
//    fun favoriteShowWindow(rid: String?,view1: View) {
//        dataSource.favoriteShowWindow(rid, object : IDataSource.HttpRequestCallBack {
//
//            override fun onStart() {
//                view1.isEnabled = false
//            }
//
//            override fun onSuccess(json: String) {
//                view1.isEnabled = true
//                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
//                if (favoriteBean.success) {
//                    view.setFavorite(true)
//                } else {
//                    view.showError(favoriteBean.status.message)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                view1.isEnabled = true
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
//    }


    /**
     *  取消喜欢
     */
//    fun unfavoriteShowWindow(rid: String?, view1: View) {
//        dataSource.unfavoriteShowWindow(rid, object : IDataSource.HttpRequestCallBack {
//
//            override fun onStart() {
//                view1.isEnabled = false
//            }
//
//            override fun onSuccess(json: String) {
//                view1.isEnabled = true
//                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
//                if (favoriteBean.success) {
//                    view.setFavorite(false)
//                } else {
//                    view.showError(favoriteBean.status.message)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                view1.isEnabled = true
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
//    }



    /**
     * 加载猜你喜欢
     */
    override fun getGuessLike(rid: String) {
        dataSource.getGuessLike(rid, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setGuessLikeData(editorRecommendBean.data.products)
                } else {
                    view.showError(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     *  获取相关橱窗
     */
    fun getRelateShowWindow(rid: String) {
        dataSource.getRelateShowWindow(rid, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val discoverLifeBean = JsonUtil.fromJson(json, DiscoverLifeBean::class.java)
                if (discoverLifeBean.success) {
                    view.setRelateShowWindowData(discoverLifeBean.data.shop_windows)
                } else {
                    view.showError(discoverLifeBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 关注用户
     */
    override fun focusUser(uid: String, view1: View) {
        dataSource.focusUser(uid, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val userFocusState = JsonUtil.fromJson(json, UserFocusState::class.java)
                if (userFocusState.success) {
                    view.setUserFocusState(true)
                } else {
                    view.showError(userFocusState.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 取消关注用户
     */
    override fun unfocusUser(uid: String, view1: View) {
        dataSource.unfocusUser(uid, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val userFocusState = JsonUtil.fromJson(json, UserFocusState::class.java)
                if (userFocusState.success) {
                    view.setUserFocusState(false)
                } else {
                    view.showError(userFocusState.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 发送橱窗评论
     */
//    override fun sendComment(rid: String, pid: String, content: String) {
//        dataSource.sendComment(rid,pid,content,object : IDataSource.HttpRequestCallBack {
//            override fun onSuccess(json: String) {
//                val showWindowCommentBean = JsonUtil.fromJson(json, ShowWindowCommentBean::class.java)
//                if (showWindowCommentBean.success) {
//                    view.setCommentState()
//                } else {
//                    view.showError(showWindowCommentBean.status.message)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
//    }

    /**
     * 对评论点赞
     */
    override fun praiseComment(comment_id: String,isPraise:Boolean,position: Int, view1: View, isSubAdapter: Boolean) {
        dataSource.praiseComment(comment_id,isPraise,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    view.setPraiseCommentState(!isPraise, position, isSubAdapter)
                } else {
                    view.showError(favoriteBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 根据父评论加载子评论
     */
    override fun loadMoreSubComments(item: CommentBean, view1: View, adapter: ShopWindowDetailCommentListAdapter) {
        dataSource.loadMoreSubComments(item.subCommentPage, item.comment_id, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val subCommentsBean = JsonUtil.fromJson(json, SubCommentsBean::class.java)
                if (subCommentsBean.success) {
                    val sub_comments = item.sub_comments
                    if (item.subCommentPage == 1) sub_comments.clear()
                    if (subCommentsBean.data != null && subCommentsBean.data.comments != null) sub_comments.addAll(subCommentsBean.data.comments)
                    item.subCommentPage++
                    adapter.notifyDataSetChanged()
                } else {
                    view.showError(subCommentsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 提交评论
     */
    override fun submitComment(rid: String, pid: String,replyId:String, content: String, sendButton: Button) {
        dataSource.submitComment(rid, pid,replyId, content, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                sendButton.isEnabled = false
            }

            override fun onSuccess(json: String) {
                sendButton.isEnabled = true
                val commentSuccessBean = JsonUtil.fromJson(json, CommentSuccessBean::class.java)
                if (commentSuccessBean.success) {
                    view.noticeCommentSuccess(commentSuccessBean.data)
                    ToastUtil.showSuccess("发送评论成功")
                } else {
                    view.showError(commentSuccessBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                sendButton.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 喜欢橱窗
     */
    fun favoriteShowWindow(rid: String, view1: ImageView, isFavorite: Boolean, textViewLikeCount: TextView) {
        dataSource.favoriteShowWindow(rid,isFavorite,object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(!isFavorite,view1,textViewLikeCount)
                } else {
                    view.showError(favoriteBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view1.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}