package com.lexivip.lexi.discoverLifeAesthetics

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.net.NetStatusBean
import java.io.IOException

class ShowWindowCommentPresenter(view: ShowWindowCommentContract.View) : ShowWindowCommentContract.Presenter {
    private var view: ShowWindowCommentContract.View = checkNotNull(view)

    private val dataSource: ShowWindowCommentModel by lazy { ShowWindowCommentModel() }

    private var page: Int = 1

    /**
     * 加载数据
     */
    override fun loadData(rid: String, isRefresh: Boolean) {
        if (isRefresh) this.page = 1

        dataSource.loadData(page, rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e("" + json)
                view.dismissLoadingView()
                val showWindowCommentBean = JsonUtil.fromJson(json, ShowWindowCommentListBean::class.java)
                if (showWindowCommentBean.success) {
                    view.setNewData(showWindowCommentBean.data.comments)
                    ++page
                } else {
                    view.showError(showWindowCommentBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载更多
     */
    override fun loadMoreData(rid: String) {
        dataSource.loadData(page, rid, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val showWindowCommentBean = JsonUtil.fromJson(json, ShowWindowCommentListBean::class.java)
                if (showWindowCommentBean.success) {
                    val comments = showWindowCommentBean.data.comments
                    if (comments.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(comments)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(showWindowCommentBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 对评论点赞
     */
    override fun praiseComment(comment_id: String, isPraise: Boolean, position: Int, view1: View, isSubAdapter: Boolean) {
        dataSource.praiseComment(comment_id, isPraise, object : IDataSource.HttpRequestCallBack {
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
     * 对子评论点赞
     */
    override fun praiseComment(view1: View, commentBean: CommentBean, adapter: ShowWindowSubCommentListAdapter) {
        dataSource.praiseComment(commentBean.comment_id, commentBean.is_praise, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    if (commentBean.is_praise) {
                        commentBean.is_praise = false
                        commentBean.praise_count -= 1
                    } else {
                        commentBean.is_praise = true
                        commentBean.praise_count += 1
                    }
                    adapter.notifyDataSetChanged()
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
    override fun loadMoreSubComments(item: CommentBean, view1: View, adapter: ShowWindowCommentListAdapter) {
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
    override fun submitComment(rid: String, pid: String, replyId: String, content: String, sendButton: Button) {
        dataSource.submitComment(rid, pid, replyId, content, object : IDataSource.HttpRequestCallBack {

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
                LogUtil.e("submitComment发送评论失败")
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    fun favoriteShowWindow(rid: String, view1: ImageView, isFavorite: Boolean, textViewLikeCount: TextView) {
        dataSource.favoriteShowWindow(rid, isFavorite, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(!isFavorite, view1, textViewLikeCount)
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