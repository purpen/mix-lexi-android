package com.thn.lexi.mine
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class FavoriteDesignPresenter(view: FavoriteDesignContract.View) : FavoriteDesignContract.Presenter {

    private var view:FavoriteDesignContract.View = checkNotNull(view)

    private val dataSource: FavoriteDesignModel by lazy { FavoriteDesignModel() }
    private var page:Int =1

    override fun loadData() {
        dataSource.loadData(page,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val designPavilionListBean = JsonUtil.fromJson(json, DesignPavilionListBean::class.java)
                if (designPavilionListBean.success) {
                    view.setNewData(designPavilionListBean.data.stores)
                    ++page
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