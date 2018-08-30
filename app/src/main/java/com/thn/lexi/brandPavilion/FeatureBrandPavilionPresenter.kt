package com.thn.lexi.brandPavilion

import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.index.explore.BrandPavilionListBean
import java.io.IOException

class FeatureBrandPavilionPresenter(view: FeatrueBrandPavilionContract.View) : FeatrueBrandPavilionContract.Presenter {
    private var view: FeatrueBrandPavilionContract.View = checkNotNull(view)

    private val dataSource: FeatureBrandPavilionModel by lazy { FeatureBrandPavilionModel() }

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
                val brandPavilionListBean = JsonUtil.fromJson(json, BrandPavilionListBean::class.java)
                if (brandPavilionListBean.success) {
                    view.setNewData(brandPavilionListBean.data.stores)
                    ++page
                } else {
                    view.showError(brandPavilionListBean.status.message)
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
                val brandPavilionListBean = JsonUtil.fromJson(json, BrandPavilionListBean::class.java)
                if (brandPavilionListBean.success) {
                    val stores = brandPavilionListBean.data.stores
                    if (stores.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(stores)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(brandPavilionListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}