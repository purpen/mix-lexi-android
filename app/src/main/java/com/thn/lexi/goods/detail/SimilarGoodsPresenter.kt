package com.thn.lexi.goods.detail

import com.basemodule.tools.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class SimilarGoodsPresenter(view: SimilarGoodsContract.View) {
    private var view: SimilarGoodsContract.View = checkNotNull(view)

    private val dataSource: SimilarGoodsModel by lazy { SimilarGoodsModel() }

    fun loadData(page: Int) {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                val similarGoodsBean = JsonUtil.fromJson(json, SimilarGoodsBean::class.java)
                if (similarGoodsBean.success) {
                    view.setNewData(similarGoodsBean.data.products)
                } else {
                    view.showError(similarGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
//                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    fun loadMoreData(page: Int) {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                val similarGoodsBean = JsonUtil.fromJson(json, SimilarGoodsBean::class.java)
                if (similarGoodsBean.success) {
                    val products = similarGoodsBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                    }
                    view.addData(similarGoodsBean.data.products)
                } else {
                    view.showError(similarGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}