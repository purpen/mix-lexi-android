package com.lexivip.lexi.index.explore.collection

import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import java.io.IOException

class CollectionDetailPresenter(view: CollectionDetailContract.View) : CollectionDetailContract.Presenter {
    private var view: CollectionDetailContract.View = checkNotNull(view)

    private val dataSource: CollectionDetailModel by lazy { CollectionDetailModel() }

    private var page: Int = 1
    private var id: String = ""

    /**
     * 加载数据
     */
    override fun loadData(id: String, isRefresh: Boolean) {
        if (isRefresh) this.page = 1
        this.id = id
        dataSource.loadData(id, page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val collectionDetailBean = JsonUtil.fromJson(json, CollectionDetailBean::class.java)
                if (collectionDetailBean.success) {
                    view.setNewData(collectionDetailBean.data)
                    page++
                } else {
                    view.showError(collectionDetailBean.status.message)
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
    fun loadMoreData() {
        dataSource.loadData(id, page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val collectionDetailBean = JsonUtil.fromJson(json, CollectionDetailBean::class.java)
                if (collectionDetailBean.success) {
                    val products = collectionDetailBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(collectionDetailBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}