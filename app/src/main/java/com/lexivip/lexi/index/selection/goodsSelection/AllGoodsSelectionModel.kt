package com.lexivip.lexi.index.selection.goodsSelection

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.index.selection.AllCustomMadeActivity
import com.lexivip.lexi.index.selection.freePostage.AllFreePostageActivity
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException
import java.util.*

open class AllGoodsSelectionModel {

    fun loadData(whichPage: String, page: Int, sortType: String, minePrice: String, maxPrice: String, cids: String, is_free_postage: String, is_preferential: String, is_custom_made: String, sort_newest: String, callBack: IDataSource.HttpRequestCallBack) {
        var params: HashMap<String, Any>? = null
        var url = ""
        when (whichPage) {
            AllCustomMadeActivity::class.java.simpleName -> {
                params = ClientParamsAPI.getDefaultParams()
                params["page"] = "$page"
                url = URL.PRODUCTS_CUSTOM_MADE
            }
            AllFreePostageActivity::class.java.simpleName -> {
                params = ClientParamsAPI.getAllFreePostageParams(page, sortType, minePrice, maxPrice, cids, sort_newest)
                url = URL.GOODS_FREE_POSTAGE
            }

            AllGoodsSelectionActivity::class.java.simpleName -> {
                params = ClientParamsAPI.getAllEditorRecommendParams(page, sortType, minePrice, maxPrice, cids, is_free_postage, is_preferential, is_custom_made, sort_newest)
                url = URL.GOOD_SELECTION_URL
            }
        }

        HttpRequest.sendRequest(HttpRequest.GET, url, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callBack.onStart()
            }

            override fun onSuccess(json: String) {
                callBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callBack.onFailure(e)
            }
        })
    }

    /**
     * 获取商品分类
     */
    fun getGoodsClassify(callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_CATEGORIES_URL, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callBack.onStart()
            }

            override fun onSuccess(json: String) {
                callBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callBack.onFailure(e)
            }
        })
    }

}


