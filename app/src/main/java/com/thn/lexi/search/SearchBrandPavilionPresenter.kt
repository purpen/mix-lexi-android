package com.thn.lexi.search
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.mine.designPavilion.DesignPavilionListBean
import com.thn.lexi.net.NetStatusBean
import java.io.IOException

class SearchBrandPavilionPresenter(view: SearchBrandPavilionContract.View) : SearchBrandPavilionContract.Presenter {

    private var view: SearchBrandPavilionContract.View = checkNotNull(view)

    private val dataSource: SearchBrandModel by lazy { SearchBrandModel() }
    private var page:Int =1

    private var searchString=""

    override fun loadData(searchString:String) {
        this.searchString = searchString
        dataSource.loadData(page,searchString,object : IDataSource.HttpRequestCallBack {
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
        dataSource.loadData(page,searchString,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
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
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 关注/取消品牌馆
     */
    override fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, position: Int) {
        dataSource.focusBrandPavilion(store_rid,isFavorite,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    view.setBrandPavilionFocusState(isFavorite,position)
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}