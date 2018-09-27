package com.thn.lexi.mine.enshrine
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
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

}