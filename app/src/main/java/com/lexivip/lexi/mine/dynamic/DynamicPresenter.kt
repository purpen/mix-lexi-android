package com.lexivip.lexi.mine.dynamic

import android.view.View
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.discoverLifeAesthetics.UserFocusState
import com.lexivip.lexi.net.NetStatusBean
import com.lexivip.lexi.user.login.UserProfileUtil
import java.io.IOException

class DynamicPresenter(view: DynamicContract.View) : DynamicContract.Presenter {

    private var view: DynamicContract.View = checkNotNull(view)

    private val dataSource: DynamicModel by lazy { DynamicModel() }

    private var page: Int = 1

    private var uid = UserProfileUtil.getUserId()

    override fun loadData(isRefresh: Boolean, uid: String) {
        this.uid = uid
        if (isRefresh) page = 1
        dataSource.loadData(page, uid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val dynamicBean = JsonUtil.fromJson(json, DynamicBean::class.java)
                if (dynamicBean.success) {
                    view.setNewData(dynamicBean.data)
                    ++page
                } else {
                    view.showError(dynamicBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    override fun loadMoreData() {
        dataSource.loadData(page, uid, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val dynamicBean = JsonUtil.fromJson(json, DynamicBean::class.java)
                if (dynamicBean.success) {
                    val shopWindows = dynamicBean.data.lines
                    if (shopWindows.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(dynamicBean.data)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(dynamicBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 删除橱窗
     */
    fun deleteShopWindow(rid: String, position: Int) {
        dataSource.deleteShopWindow(rid,object :IDataSource.HttpRequestCallBack{
            override fun onSuccess(json: String) {
                val statusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (statusBean.success){
                    view.deleteShopWindow(position)
                }else{
                    view.showError(statusBean.status.message)
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
    override fun favoriteShowWindow(rid: String,isFavorite:Boolean,position: Int, view1: View) {
        dataSource.favoriteShowWindow(rid,isFavorite, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(!isFavorite, position)
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
     * 关注用户
     */
    override fun focusUser(uid: String, v: View, focusState: Int) {
        dataSource.focusUser(uid,focusState, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
            }

            override fun onSuccess(json: String) {
                v.isEnabled = true
                val userFocusState = JsonUtil.fromJson(json, UserFocusState::class.java)
                if (userFocusState.success) {
                    view.setUserFocusState(userFocusState.data.followed_status)
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