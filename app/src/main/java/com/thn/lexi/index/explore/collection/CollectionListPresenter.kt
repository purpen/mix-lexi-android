package com.thn.lexi.index.explore.collection
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.index.explore.GoodsCollectionBean
import java.io.IOException

class CollectionListPresenter(view: CollectionListContract.View) : CollectionListContract.Presenter {
    private var view: CollectionListContract.View = checkNotNull(view)

    private val dataSource: CollectionListModel by lazy { CollectionListModel() }

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
                val goodsCollectionBean = JsonUtil.fromJson(json, GoodsCollectionBean::class.java)
                if (goodsCollectionBean.success) {
                    view.setNewData(goodsCollectionBean.data.collections)
                    ++page
                } else {
                    view.showError(goodsCollectionBean.status.message)
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
                val goodsCollectionBean = JsonUtil.fromJson(json, GoodsCollectionBean::class.java)
                if (goodsCollectionBean.success) {
                    val collections = goodsCollectionBean.data.collections
                    if (collections.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(collections)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(goodsCollectionBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}