package com.thn.lexi.mine.dynamic
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.mine.designPavilion.DesignPavilionListBean
import java.io.IOException

class DynamicPresenter(view: DynamicContract.View) : DynamicContract.Presenter {

    private var view: DynamicContract.View = checkNotNull(view)

    private val dataSource: DynamicModel by lazy { DynamicModel() }

    private var page:Int =1

    override fun loadData(isRefresh: Boolean) {

        if (isRefresh) page =1

        dataSource.loadData(page,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val dynamicBean = JsonUtil.fromJson(json, DynamicBean::class.java)
                if (dynamicBean.success) {
                    view.setNewData(dynamicBean.data)
                    ++page
                } else {
                    view.showError(dynamicBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    override fun loadMoreData() {
        dataSource.loadData(page,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val designPavilionListBean = JsonUtil.fromJson(json, DesignPavilionListBean::class.java)
                if (designPavilionListBean.success) {
                    val stores = designPavilionListBean.data.stores
                    if (stores.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(stores)
                        ++page
                    }
                } else {
                    view.showError(designPavilionListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}