package com.thn.lexi.discoverLifeAesthetics

import android.view.View
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.net.NetStatusBean
import java.io.IOException

class ShowWindowCommentPresenter(view: ShowWindowCommentContract.View) : ShowWindowCommentContract.Presenter {
    private var view: ShowWindowCommentContract.View = checkNotNull(view)

    private val dataSource: ShowWindowCommentModel by lazy { ShowWindowCommentModel() }

    private var page: Int = 1

    /**
     * 加载数据
     */
    override fun loadData(rid: String,isRefresh: Boolean) {
        if (isRefresh) this.page = 1

        dataSource.loadData(page,rid,object : IDataSource.HttpRequestCallBack {
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
                        view.setNewData(ArrayList<ShowWindowCommentListBean.DataBean.CommentsBean>())
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
        dataSource.loadData(page,rid,object : IDataSource.HttpRequestCallBack {

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
     * 喜欢橱窗
     */
    fun favoriteShowWindow(rid: String?, position: Int, view1: View) {
        dataSource.favoriteShowWindow(rid, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(true, position)
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