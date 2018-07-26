package com.thn.lexi.goods.explore
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.Constants
import com.thn.lexi.R
import com.thn.lexi.goods.bean.FavoriteBean
import com.thn.lexi.goods.selection.GoodsData
import java.io.IOException

class ExplorePresenter(view: ExploreContract.View) : ExploreContract.Presenter {


    private var view: ExploreContract.View = checkNotNull(view)

    private val dataSource: ExploreModel by lazy { ExploreModel() }

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

    /**
     * 获取商品分类
     */
    fun getGoodsClass() {
        dataSource.getGoodsClass( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val goodsClassBean = JsonUtil.fromJson(json, GoodsClassBean::class.java)
                if (goodsClassBean.status.code==Constants.SUCCESS) {
                    view.setGoodsClassData(goodsClassBean.data.categories)
                } else {
                    view.showError(goodsClassBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}