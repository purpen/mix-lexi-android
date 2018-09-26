package com.thn.lexi.search

import android.view.View
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.discoverLifeAesthetics.UserFocusState
import java.io.IOException

class SearchUserListPresenter(view: SearchUserListContract.View) : SearchUserListContract.Presenter {
    private var view: SearchUserListContract.View = checkNotNull(view)

    private val dataSource: SearchUserListModel by lazy { SearchUserListModel() }

    private var page: Int = 1
    /**
     * 加载数据
     */
    override fun loadData(searchString:String,isRefresh: Boolean) {
        if (isRefresh) this.page = 1

        dataSource.loadData(searchString,page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val searchUserBean = JsonUtil.fromJson(json, SearchUserBean::class.java)
                if (searchUserBean.success) {
                    view.setNewData(searchUserBean.data.users)
                    ++page
                } else {
                    view.showError(searchUserBean.status.message)
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
    override fun loadMoreData(searchString :String) {
        dataSource.loadData(searchString,page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val searchUserBean = JsonUtil.fromJson(json, SearchUserBean::class.java)
                if (searchUserBean.success) {
                    val users = searchUserBean.data.users
                    if (users.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(users)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(searchUserBean.status.message)
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
    override fun focusUser(uid: String, v: View, focusState: Int, position: Int) {
        dataSource.focusUser(uid,focusState, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
            }

            override fun onSuccess(json: String) {
                v.isEnabled = true
                val userFocusState = JsonUtil.fromJson(json, UserFocusState::class.java)
                if (userFocusState.success) {
                    view.setFocusState(userFocusState.data.followed_status,position)
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