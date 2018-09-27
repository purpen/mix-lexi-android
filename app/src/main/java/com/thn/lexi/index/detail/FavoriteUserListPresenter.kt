package com.thn.lexi.index.detail

import android.view.View
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.discoverLifeAesthetics.UserFocusState
import java.io.IOException

class FavoriteUserListPresenter(view: FavoriteUserListContract.View) : FavoriteUserListContract.Presenter {
    private var view: FavoriteUserListContract.View = checkNotNull(view)

    private val dataSource: FavoriteUserListModel by lazy { FavoriteUserListModel() }

    private var page: Int = 1
    /**
     * 加载数据
     */
    override fun loadData(goodsId:String,isRefresh: Boolean) {
        if (isRefresh) this.page = 1

        dataSource.loadData(goodsId,page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()


                val favoriteGoodsUsersBean = JsonUtil.fromJson(json, FavoriteGoodsUsersBean::class.java)
                if (favoriteGoodsUsersBean.success) {
                    view.setNewData(favoriteGoodsUsersBean.data.product_like_users)
                    ++page
                } else {
                    view.showError(favoriteGoodsUsersBean.status.message)
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
    override fun loadMoreData(goodsId :String) {
        dataSource.loadData(goodsId,page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val favoriteGoodsUsersBean = JsonUtil.fromJson(json, FavoriteGoodsUsersBean::class.java)
                if (favoriteGoodsUsersBean.success) {
                    val stores = favoriteGoodsUsersBean.data.product_like_users
                    if (stores.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(stores)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(favoriteGoodsUsersBean.status.message)
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