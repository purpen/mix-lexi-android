package com.lexivip.lexi.publishShopWindow
import com.basemodule.tools.LogUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.net.NetStatusBean
import com.lexivip.lexi.search.HotSearchBean
import java.io.IOException

class ShopWindowTagsPresenter(view: ShopWindowTagsContract.View) : ShopWindowTagsContract.Presenter {
    private var view: ShopWindowTagsContract.View = checkNotNull(view)

    private val dataSource: ShopWindowTagsModel by lazy { ShopWindowTagsModel() }

    private var page: Int = 1
    private var keyWord: String = ""

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
        page = 1
        this.keyWord = keyWord
        dataSource.getFuzzyWordList(keyWord,page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {

            }

            override fun onSuccess(json: String) {
                val fuzzyMatchTagsBean = JsonUtil.fromJson(json, FuzzyMatchTagsBean::class.java)
                if (fuzzyMatchTagsBean.success) {
                    view.setFuzzyWordListData(fuzzyMatchTagsBean.data.keywords)
                    page++
                } else {
                    view.showError(fuzzyMatchTagsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载更多模糊匹配
     */
    override fun getMoreFuzzyWordList() {
        dataSource.getFuzzyWordList(keyWord,page,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val fuzzyMatchTagsBean = JsonUtil.fromJson(json, FuzzyMatchTagsBean::class.java)

                if (fuzzyMatchTagsBean.success) {
                    val keyWords = fuzzyMatchTagsBean.data.keywords
                    if (keyWords.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.setMoreFuzzyWordListData(fuzzyMatchTagsBean.data.keywords)
                        ++page
                    }
                } else {
                    view.showError(fuzzyMatchTagsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 获取热门店铺标签
     */
    fun getHotShopWindowTags() {
        dataSource.getHotShopWindowTags(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                val hotShopWindowTagsBean = JsonUtil.fromJson(json, HotShopWindowTagsBean::class.java)
                if (hotShopWindowTagsBean.success) {
                    view.setHotTags(hotShopWindowTagsBean.data.keywords)
                } else {
                    view.showError(hotShopWindowTagsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 添加自定义橱窗标签
     */
    override fun addShopWindowTag(searchString: String) {
        dataSource.addShopWindowTag(searchString,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    view.setAddTagSuccess(searchString)
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}