package com.lexivip.lexi.search

import com.basemodule.tools.LogUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.mine.enshrine.RecentLookGoodsBean
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
                LogUtil.e("===="+json)
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
    override fun getHotRecommendPavilion() {
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
    override fun getHotSearch() {
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

    /**
     * 模糊匹配
     */
    override fun getFuzzyWordList(keyWord: String) {
        dataSource.getFuzzyWordList(keyWord,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                val fuzzyWordMatchListBean = JsonUtil.fromJson(json, FuzzyWordMatchListBean::class.java)
                if (fuzzyWordMatchListBean.success) {
                    view.setFuzzyWordListData(fuzzyWordMatchListBean.data.search_items)
                } else {
                    view.showError(fuzzyWordMatchListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}