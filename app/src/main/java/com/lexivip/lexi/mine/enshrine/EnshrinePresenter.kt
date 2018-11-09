package com.lexivip.lexi.mine.enshrine
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import java.io.IOException

class EnshrinePresenter(view: EnshrineContract.View) : EnshrineContract.Presenter {

    private var view: EnshrineContract.View = checkNotNull(view)

    private val dataSource: EnshrineModel by lazy { EnshrineModel() }

    /**
     * 获取最近查看
     */
    override fun getUserRecentLook() {
        dataSource.getUserRecentLook(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val recentLookGoodsBean = JsonUtil.fromJson(json, RecentLookGoodsBean::class.java)
                if (recentLookGoodsBean.success) {
                    view.setRecentLookData(recentLookGoodsBean.data.products)
                } else {
                    view.showError(recentLookGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 查看心愿单
     */
    override fun getWishOrder() {
        dataSource.getWishOrder(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val wishOrderBean = JsonUtil.fromJson(json, WishOrderBean::class.java)
                if (wishOrderBean.success) {
                    view.setWishOrderData(wishOrderBean.data.products)
                } else {
                    view.showError(wishOrderBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取别人最近查看
     */
    fun getOtherUserRecentLook(uid: String) {
        dataSource.getOtherUserRecentLook(uid,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val recentLookGoodsBean = JsonUtil.fromJson(json, RecentLookGoodsBean::class.java)
                if (recentLookGoodsBean.success) {
                    view.setRecentLookData(recentLookGoodsBean.data.products)
                } else {
                    view.showError(recentLookGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取别人的心愿单
     */
    fun getOtherUserWishOrder(uid: String) {
        dataSource.getOtherUserWishOrder(uid,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val wishOrderBean = JsonUtil.fromJson(json, WishOrderBean::class.java)
                if (wishOrderBean.success) {
                    view.setWishOrderData(wishOrderBean.data.products)
                } else {
                    view.showError(wishOrderBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}