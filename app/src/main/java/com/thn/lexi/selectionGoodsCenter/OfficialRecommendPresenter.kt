package com.thn.lexi.selectionGoodsCenter

import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class OfficialRecommendPresenter(view: OfficialRecommendContract.View) : OfficialRecommendContract.Presenter {
    private var view: OfficialRecommendContract.View = checkNotNull(view)

    private val dataSource: OfficialRecommendModel by lazy { OfficialRecommendModel() }

    override fun loadData(page: Int) {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val hotGoodsBean = JsonUtil.fromJson(json, HotGoodsBean::class.java)
                if (hotGoodsBean.success) {
                    view.setNewData(hotGoodsBean.data.products)
                } else {
                    view.showError(hotGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    fun loadMoreData(page: Int) {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val hotGoodsBean = JsonUtil.fromJson(json, HotGoodsBean::class.java)
                if (hotGoodsBean.success) {
                    val products = hotGoodsBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                    }
                } else {
                    view.showError(hotGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


}