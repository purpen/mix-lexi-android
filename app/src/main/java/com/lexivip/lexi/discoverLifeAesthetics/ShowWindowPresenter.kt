package com.lexivip.lexi.discoverLifeAesthetics

import android.view.View
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.net.NetStatusBean
import java.io.IOException

class ShowWindowPresenter(view: ShowWindowContract.View) : ShowWindowContract.Presenter {
    private var view: ShowWindowContract.View = checkNotNull(view)

    private val dataSource: ShowWindowModel by lazy { ShowWindowModel() }

    private var page: Int = 1
    /**
     * 加载数据
     */
    override fun loadData(isRefresh: Boolean) {
        if (isRefresh) this.page = 1

        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowListBean::class.java)
                if (showWindowBean.success) {
                    view.setNewData(showWindowBean.data.shop_windows)
                    ++page
                } else {
                    view.showError(showWindowBean.status.message)
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
    override fun loadMoreData() {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowListBean::class.java)
                if (showWindowBean.success) {
                    val shopWindows = showWindowBean.data.shop_windows
                    if (shopWindows.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(shopWindows)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(showWindowBean.status.message)
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
     * 加载关注的橱窗
     */
    fun loadFocusData(isRefresh: Boolean) {
        if (isRefresh) this.page = 1

        dataSource.loadFocusData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowListBean::class.java)
                if (showWindowBean.success) {
                    view.setNewData(showWindowBean.data.shop_windows)
                    ++page
                } else {
                    view.showError(showWindowBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 加载更多关注的橱窗
     */
    fun loadMoreFocusData() {
        dataSource.loadFocusData(page, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowListBean::class.java)
                if (showWindowBean.success) {
                    val shopWindows = showWindowBean.data.shop_windows
                    if (shopWindows.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(shopWindows)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(showWindowBean.status.message)
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
    override fun focusUser(uid: String, v:View,followState: Int, position: Int) {
        dataSource.focusUser(uid,followState, object : IDataSource.HttpRequestCallBack {
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


    /**
     * 加载相关的橱窗
     */
    fun loadRelateShopWIndow(tag: String) {
        dataSource.loadRelateShopWIndow(page,tag, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val showWindowListBean = JsonUtil.fromJson(json, ShowWindowListBean::class.java)
                if (showWindowListBean.success) {
                    view.setNewData(showWindowListBean.data.shop_windows)
                    page++
                } else {
                    view.showError(showWindowListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载更多包含橱窗
     */
    fun loadMoreRelateShopWindow(tag: String) {
        dataSource.loadRelateShopWIndow(page,tag,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowListBean::class.java)
                if (showWindowBean.success) {
                    val shopWindows = showWindowBean.data.shop_windows
                    if (shopWindows.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(shopWindows)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(showWindowBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}