package com.thn.lexi.index.discover

import android.view.View
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.discoverLifeAesthetics.UserFocusState
import java.io.IOException

class ArticleDetailPresenter(view: ArticleDetailContract.View) : ArticleDetailContract.Presenter {

    private var view: ArticleDetailContract.View = checkNotNull(view)

    private val dataSource: ArticleDetailModel by lazy { ArticleDetailModel() }

    private var rid:String=""

    override fun loadData(isRefresh: Boolean, rid: String) {
        this.rid = rid
        dataSource.loadData(rid,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val articleDetailBean = JsonUtil.fromJson(json, ArticleDetailBean::class.java)
                if (articleDetailBean.success) {
                    view.setData(articleDetailBean.data)
                } else {
                    view.showError(articleDetailBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 关注用户
     */
    override fun focusUser(uid: String, v: View, isFollow: Boolean) {
        dataSource.focusUser(uid,isFollow, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
            }

            override fun onSuccess(json: String) {
                v.isEnabled = true
                val userFocusState = JsonUtil.fromJson(json, UserFocusState::class.java)
                if (userFocusState.success) {
                    view.setFocusState(userFocusState.data.followed_status)
                } else {
                    view.showError(userFocusState.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                v.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}