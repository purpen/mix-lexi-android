package com.thn.lexi.index.explore.goodsIn100

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class AllGoodsIn100Model {

    fun loadData(page: Int, sortType: String, minePrice: String, maxPrice: String, cids: String,is_free_postage: String, is_preferential: String,is_custom_made: String,sort_newest:String,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getAllEditorRecommendParams(page, sortType,minePrice, maxPrice, cids,is_free_postage,is_preferential,is_custom_made,sort_newest)

        HttpRequest.sendRequest(HttpRequest.GET, URL.GOOD100_URL, params, object : IDataSource.HttpRequestCallBack {
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

    fun getLookPeople(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["code"] = "affordable_goods"
        HttpRequest.sendRequest(HttpRequest.GET, URL.USER_BROWSE_RECORDS, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }

            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
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


