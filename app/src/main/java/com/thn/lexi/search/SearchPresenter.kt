package com.thn.lexi.search

import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.mine.enshrine.RecentLookGoodsBean
import java.io.IOException

class SearchPresenter(view: SearchContract.View) : SearchContract.Presenter {
    private var view: SearchContract.View = checkNotNull(view)

    private val dataSource: SearchModel by lazy { SearchModel() }

    /**
     * 获取最近查看
     */
    override fun getUserRecentLook() {
        dataSource.getUserRecentLook(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val recentLookGoodsBean = JsonUtil.fromJson(json, RecentLookGoodsBean::class.java)
                if (recentLookGoodsBean.success) {
                    view.setRecentLookData(recentLookGoodsBean.data.products)
                } else {
                    view.showError(recentLookGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 热门推荐品牌馆
     */
    fun getHotRecommendPavilion() {
        dataSource.getHotRecommendPavilion(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                val searchHotRecommendPavilionBean = JsonUtil.fromJson(json, SearchHotRecommendPavilionBean::class.java)
                if (searchHotRecommendPavilionBean.success) {
                    view.setHotRecommendPavilionData(searchHotRecommendPavilionBean.data.hot_recommends)
                } else {
                    view.showError(searchHotRecommendPavilionBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取热门搜索
     */
    fun getHotSearch() {
        dataSource.getHotSearch(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                val hotSearchBean = JsonUtil.fromJson(json, HotSearchBean::class.java)
                if (hotSearchBean.success) {
                    view.setHotSearchData(hotSearchBean.data.search_items)
                } else {
                    view.showError(hotSearchBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}