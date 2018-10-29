package com.lexivip.lexi.discoverLifeAesthetics

import android.view.View
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

    private var subCommentPage = 1
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
                view.dismissLoadingView()
                val showWindowCommentBean = JsonUtil.fromJson(json, ShowWindowCommentListBean::class.java)

                // //TODO 构造假数据
                showWindowCommentBean.success = true

                if (showWindowCommentBean.success) {
                    //TODO 构造假数据
                    view.setNewData(ArrayList<CommentBean>())
//                    view.setNewData(showWindowCommentBean.data.comments)
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
    override fun loadMoreSubComments(comment_id: String, position: Int, view1: View) {
        dataSource.loadMoreSubComments(subCommentPage, comment_id, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val subCommentsBean = JsonUtil.fromJson(json, SubCommentsBean::class.java)
                if (subCommentsBean.success) {
                    view.addSubCommentsData(position, subCommentsBean.data.comments)
                    subCommentPage++
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

}