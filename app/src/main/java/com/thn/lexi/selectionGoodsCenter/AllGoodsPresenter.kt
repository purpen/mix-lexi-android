package com.thn.lexi.selectionGoodsCenter

import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class AllGoodsPresenter(view: AllGoodsContract.View) : AllGoodsContract.Presenter {
    private var view: AllGoodsContract.View = checkNotNull(view)

    private val dataSource: AllGoodsModel by lazy { AllGoodsModel() }

    override fun loadData(page: Int, sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String) {
        dataSource.loadData(page,sortType,profitType,filterCondition,minePrice,maxPrice,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val hotGoodsBean = JsonUtil.fromJson(json, HotGoodsBean::class.java)
                if (hotGoodsBean.success) {
                    view.setNewData(hotGoodsBean.data.products)
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
    fun loadMoreData(page: Int,sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String) {
        dataSource.loadData(page,sortType,profitType,filterCondition,minePrice,maxPrice, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val hotGoodsBean = JsonUtil.fromJson(json, HotGoodsBean::class.java)
                if (hotGoodsBean.success) {
                    val products = hotGoodsBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
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


}