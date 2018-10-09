package com.thn.lexi.discoverLifeAesthetics

import android.view.View
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.net.NetStatusBean
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
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowBean::class.java)
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
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowBean::class.java)
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
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowBean::class.java)
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
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowBean::class.java)
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
                    view.showError(showWindowBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}