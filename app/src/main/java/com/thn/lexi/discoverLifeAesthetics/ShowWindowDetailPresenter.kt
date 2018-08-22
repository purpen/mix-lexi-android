package com.thn.lexi.discoverLifeAesthetics

import android.view.View
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.index.explore.EditorRecommendBean
import com.thn.lexi.index.selection.DiscoverLifeBean
import com.thn.lexi.net.NetStatusBean
import java.io.IOException

class ShowWindowDetailPresenter(view: ShowWindowDetailContract.View) : ShowWindowDetailContract.Presenter {
    private var view: ShowWindowDetailContract.View = checkNotNull(view)

    private val dataSource: ShowWindowDetailModel by lazy { ShowWindowDetailModel() }


    /**
     * 加载详情数据
     */
    override fun loadData(rid: String,isRefresh: Boolean) {
        dataSource.loadData(rid,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
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
    fun favoriteShowWindow(rid: String?,view1: View) {
        dataSource.favoriteShowWindow(rid, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(true)
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
     *  取消喜欢
     */
    fun unfavoriteShowWindow(rid: String?, view1: View) {
        dataSource.unfavoriteShowWindow(rid, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view1.isEnabled = false
            }

            override fun onSuccess(json: String) {
                view1.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(true)
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
     * 加载猜你喜欢
     */
    override fun getGuessLike() {
        dataSource.getGuessLike( object : IDataSource.HttpRequestCallBack {
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
    fun getRelateShowWindow() {
        dataSource.getRelateShowWindow( object : IDataSource.HttpRequestCallBack {
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

}