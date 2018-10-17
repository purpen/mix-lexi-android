package com.lexivip.lexi.brandPavilion

import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.explore.ExploreBannerBean
import java.io.IOException

class SelectionBrandPavilionPresenter(view: SelectionBrandPavilionContract.View) : SelectionBrandPavilionContract.Presenter {
    private var view: SelectionBrandPavilionContract.View = checkNotNull(view)

    private val dataSource: SelectionBrandPavilionModel by lazy { SelectionBrandPavilionModel() }

    /**
     * 获取精选品牌馆
     */
    override fun getSelectionPavilion() {
        dataSource.getSelectionPavilion(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val selectionBrandPavilionListBean = JsonUtil.fromJson(json, SelectionBrandPavilionListBean::class.java)
                if (selectionBrandPavilionListBean.success) {
                    view.setSelectionPavilionData(selectionBrandPavilionListBean.data.handpick_store)
                } else {
                    view.showError(selectionBrandPavilionListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    override fun getBanners() {
        dataSource.getBanners(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                val exploreBannerBean = JsonUtil.fromJson(json, ExploreBannerBean::class.java)
                if (exploreBannerBean.success) {
                    view.setBannerData(exploreBannerBean.data.banner_images)
                } else {
                    view.showError(exploreBannerBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}