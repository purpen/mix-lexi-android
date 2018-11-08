package com.lexivip.lexi.mine.designPavilion

import android.view.View
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.net.NetStatusBean
import java.io.IOException

class FavoriteDesignPresenter(view: FavoriteDesignContract.View) : FavoriteDesignContract.Presenter {

    private var view: FavoriteDesignContract.View = checkNotNull(view)

    private val dataSource: FavoriteDesignModel by lazy { FavoriteDesignModel() }
    private var page: Int = 1

    override fun loadData(b: Boolean) {
        if (b) page = 1
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val designPavilionListBean = JsonUtil.fromJson(json, DesignPavilionListBean::class.java)
                if (designPavilionListBean.success) {
                    view.setNewData(designPavilionListBean.data.stores)
                    ++page
                } else {
                    view.showError(designPavilionListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载别人的关注品牌馆
     */
    override fun loadData(b: Boolean,uid:String) {
        if (b) page = 1
        dataSource.loadData(page,uid,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val designPavilionListBean = JsonUtil.fromJson(json, DesignPavilionListBean::class.java)
                if (designPavilionListBean.success) {
                    view.setNewData(designPavilionListBean.data.stores)
                    ++page
                } else {
                    view.showError(designPavilionListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    override fun loadMoreData() {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val designPavilionListBean = JsonUtil.fromJson(json, DesignPavilionListBean::class.java)
                if (designPavilionListBean.success) {
                    val stores = designPavilionListBean.data.stores
                    if (stores.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(stores)
                        ++page
                    }
                } else {
                    view.showError(designPavilionListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载更多别人的关注品牌馆
     */
    override fun loadMoreData(uid: String) {
        dataSource.loadData(page,uid,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val designPavilionListBean = JsonUtil.fromJson(json, DesignPavilionListBean::class.java)
                if (designPavilionListBean.success) {
                    val stores = designPavilionListBean.data.stores
                    if (stores.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(stores)
                        ++page
                    }
                } else {
                    view.showError(designPavilionListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 关注/取消品牌馆
     */
    override fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, position: Int, v: View) {
        dataSource.focusBrandPavilion(store_rid, isFavorite, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
                view.setBrandPavilionFocusState(isFavorite, position)
            }
            override fun onSuccess(json: String) {
                v.isEnabled = true
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
//                    view.setBrandPavilionFocusState(isFavorite, position)
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                v.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}