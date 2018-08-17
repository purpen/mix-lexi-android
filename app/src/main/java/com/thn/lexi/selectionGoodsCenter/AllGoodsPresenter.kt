package com.thn.lexi.selectionGoodsCenter

import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class AllGoodsPresenter(view: AllGoodsContract.View) : AllGoodsContract.Presenter {
    private var view: AllGoodsContract.View = checkNotNull(view)

    private val dataSource: AllGoodsModel by lazy { AllGoodsModel() }

    private var sortType: String = SORT_TYPE_DEFAULT


    private var profitType: String = PROFIT_TYPE_DEFAULT

    private var filterCondition: String = ""

    private var minePrice: String = ""

    private var maxPrice: String = ""

    private var curPage: Int = 1

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

    override fun loadData() {
        loadData(curPage, sortType, profitType, filterCondition, minePrice, maxPrice)
    }


    override fun loadData(page: Int, sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String) {
        this.curPage = page
        this.sortType = sortType
        this.profitType = profitType
        this.filterCondition = filterCondition
        this.minePrice = minePrice
        this.maxPrice = maxPrice
        dataSource.loadData(page, sortType, profitType, filterCondition, minePrice, maxPrice, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val hotGoodsBean = JsonUtil.fromJson(json, HotGoodsBean::class.java)
                if (hotGoodsBean.success) {
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
    fun loadMoreData(page: Int, sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String) {
        dataSource.loadData(page, sortType, profitType, filterCondition, minePrice, maxPrice, object : IDataSource.HttpRequestCallBack {

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


    override fun loadMoreData() {
        loadMoreData(curPage, sortType, profitType, filterCondition, minePrice, maxPrice)
    }


}