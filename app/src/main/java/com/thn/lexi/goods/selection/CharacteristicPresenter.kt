package com.thn.lexi.goods.selection
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.goods.bean.FavoriteBean
import java.io.IOException

class CharacteristicPresenter(view: CharacteristicContract.View) : CharacteristicContract.Presenter {


    private var view: CharacteristicContract.View = checkNotNull(view)

    private val dataSource: CharacteristicModel by lazy { CharacteristicModel() }

    override fun loadData(cid: String, page: Int) {
        dataSource.loadData(cid, page,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val goodsData = JsonUtil.fromJson(json, GoodsData::class.java)
                if (goodsData.success) {
                    view.setNewData(goodsData.data.products)
                } else {
                    view.showError(goodsData.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    override fun loadMoreData(cid: String, page: Int) {
        dataSource.loadData(cid, page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val goodsData = JsonUtil.fromJson(json, GoodsData::class.java)
                if (goodsData.success) {
                    val products = goodsData.data.products
                    if (products.isEmpty() ){
                        view.loadMoreEnd()
                    }else{
                        view.loadMoreComplete()
                    }
                    view.addData(products)
                } else {
                    view.showError(goodsData.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    fun favoriteGoods(rid: String, position: Int) {
        dataSource.favoriteGoods(rid, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val favoriteBean = JsonUtil.fromJson(json, FavoriteBean::class.java)
                if (favoriteBean.success) {
                   view.setFavorite(true,position)
                } else {
                    view.showError(favoriteBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    fun unfavoriteGoods(rid: String, position: Int) {
        dataSource.unfavoriteGoods(rid, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val favoriteBean = JsonUtil.fromJson(json, FavoriteBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(false,position)
                } else {
                    view.showError(favoriteBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}