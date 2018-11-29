package com.lexivip.lexi.search

import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.selectionGoodsCenter.HotGoodsBean
import java.io.IOException

class SearchGoodsPresenter(view: SearchGoodsContract.View) : SearchGoodsContract.Presenter {
    private var view: SearchGoodsContract.View = checkNotNull(view)

    private val dataSource: SearchGoodsModel by lazy { SearchGoodsModel() }

    private var sortType: String = SORT_TYPE_SYNTHESISE

    private var profitType: String = PROFIT_TYPE_DEFAULT

    private var filterCondition: String = ""

    private var minePrice: String = ""

    private var maxPrice: String = ""

    private var cids: String = ""

    private var curPage: Int = 1

    //分类编号
    private var id: String = ""

    private var isRefresh = false

    //0=全部, 1=包邮
    private var isFreePostage: String = "0"

    //是否特惠: 0=全部, 1=特惠
    private var isPreferential: String = "0"

    //是否可定制: 0=全部, 1=可定制
    private var isCustomMade: String = "0"

    //是否按最新排序: 0=否, 1=是
    private var sortNewest: String = "0"

    companion object {
        //默认排序
        const val SORT_TYPE_DEFAULT: String = "0"

        //综合排序
        const val SORT_TYPE_SYNTHESISE: String = "1"

        //价格由低到高
        const val SORT_TYPE_LOW_UP: String = "2"

        //价格由高到低
        const val SORT_TYPE_UP_LOW: String = "3"


        //利润不限
        const val PROFIT_TYPE_DEFAULT: String = "0"

        //利润低到高
        const val PROFIT_TYPE_LOW_UP: String = "1"

        //利润高到低
        const val PROFIT_TYPE_UP_LOW: String = "2"

    }

    /**
     * 获取排序类型
     */
    fun getSortType(): String {
        return this.sortType
    }

    /**
     * 获取分类Id
     */
    fun getClassifyId(): String {
        return this.id
    }


    fun getMinPrice(): String {
        return minePrice
    }

    fun getMaxPrice(): String {
        return maxPrice
    }

    /**
     * 新品
     */
    fun getSortNewest(): String {
        return sortNewest
    }

    /**
     * 是否包邮
     */
    fun isFreePostage(): String {
        return isFreePostage
    }

    /**
     * 是否特惠
     */
    fun isPreferential(): String {
        return isPreferential
    }

    /**
     * 是否订制
     */
    fun isCustomMade(): String {
        return isCustomMade
    }


    /**
     * 子分类筛选id
     */
    fun getCids(): String {
        return cids
    }

    /**
     * 默认参数加载数据
     */
    override fun loadData(isRefresh: Boolean, searchString: String) {
        if (isRefresh) this.curPage = 1

        this.filterCondition = searchString

        loadData(curPage, sortType, profitType, filterCondition, minePrice, maxPrice, cids)
    }

    /**
     * 根据用户选择条件搜索
     */
    override fun loadData(page: Int, sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String, cids: String) {
        this.curPage = page
        this.sortType = sortType
        this.profitType = profitType
        this.filterCondition = filterCondition
        this.minePrice = minePrice
        this.maxPrice = maxPrice
        this.cids = cids
        dataSource.loadData(page, sortType, profitType, filterCondition, minePrice, maxPrice, cids, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val hotGoodsBean = JsonUtil.fromJson(json, HotGoodsBean::class.java)
                if (hotGoodsBean.success) {
                    view.setGoodsCount(hotGoodsBean.data.count)
                    view.setNewData(hotGoodsBean.data.products)
                    ++curPage
                } else {
                    view.showError(hotGoodsBean.status.message)
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
    fun loadMoreData(page: Int, sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String, cids: String) {
        dataSource.loadData(page, sortType, profitType, filterCondition, minePrice, maxPrice, cids, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val hotGoodsBean = JsonUtil.fromJson(json, HotGoodsBean::class.java)
                if (hotGoodsBean.success) {
                    val products = hotGoodsBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                        ++curPage
                    }
                } else {
                    view.showError(hotGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 默认条件加载更多
     */
    override fun loadMoreData() {
        loadMoreData(curPage, sortType, profitType, filterCondition, minePrice, maxPrice, cids)
    }


    /**
     * 获取商品分类
     */
    override fun getGoodsClassify(callBacks: IDataSource.HttpRequestCallBack) {
        dataSource.getGoodsClassify(object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                callBacks.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取搜索关键字
     */
    fun getFilterCondition(): String {
        return filterCondition
    }

}