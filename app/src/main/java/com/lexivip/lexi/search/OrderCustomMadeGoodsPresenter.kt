package com.lexivip.lexi.search

import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import java.io.IOException

class OrderCustomMadeGoodsPresenter(view: OrderCustomMadeGoodsContract.View) : OrderCustomMadeGoodsContract.Presenter {
    private var view: OrderCustomMadeGoodsContract.View = checkNotNull(view)

    private val dataSource: OrderCustomMadeGoodsModel by lazy { OrderCustomMadeGoodsModel() }

    private var page = 1
    private var isRefresh = false


    /**
     * 默认参数加载数据
     */
    override fun loadData(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        if (isRefresh) this.page = 1

        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setNewData(editorRecommendBean.data.products)
                    page++
                } else {
                    view.showError(editorRecommendBean.status.message)
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
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    val products = editorRecommendBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                        page++
                    }
                } else {
                    view.showError(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}