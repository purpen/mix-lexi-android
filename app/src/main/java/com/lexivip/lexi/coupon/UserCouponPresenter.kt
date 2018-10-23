package com.lexivip.lexi.coupon

import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.detail.ShopCouponListBean
import java.io.IOException

class UserCouponPresenter(view: UserCouponContract.View) : UserCouponContract.Presenter {
    private var view: UserCouponContract.View = checkNotNull(view)

    private val dataSource: UserCouponModel by lazy { UserCouponModel() }

    private var whichPage:String = ""
    private var page: Int = 1

    /**
     * 默认参数加载数据
     */
    override fun loadData(isRefresh: Boolean,whichPage:String) {
        this.whichPage = whichPage
        if (isRefresh) this.page = 1
        dataSource.loadData(page,whichPage,object :IDataSource.HttpRequestCallBack{
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val couponListBean = JsonUtil.fromJson(json, ShopCouponListBean::class.java)
                if (couponListBean.success) {
                    view.setNewData(couponListBean.data.coupons)
                    page++
                } else {
                    view.showError(couponListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }



    /**
     * 默认条件加载更多
     */
    override fun loadMoreData() {
        dataSource.loadData(page,whichPage,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val storyListBean = JsonUtil.fromJson(json, ShopCouponListBean::class.java)
                if (storyListBean.success) {
                    val coupons = storyListBean.data.coupons
                    if (coupons.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(coupons)
                        page++
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(storyListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}