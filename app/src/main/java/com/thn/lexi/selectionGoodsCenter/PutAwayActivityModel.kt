package com.thn.lexi.selectionGoodsCenter

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class PutAwayActivityModel {

    fun loadData(page: Int, callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getHotGoodsParams(page)

        HttpRequest.sendRequest(HttpRequest.GET, URL.HOT_GOODS_URL, params, object : IDataSource.HttpRequestCallBack {
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
     * 获取Banner
     */
    fun getBanners(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET, URL.SELECTION_BANNER_URL, params, object : IDataSource.HttpRequestCallBack {
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


    fun putAwayGoods(rid: String, store_rid: String, content: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getPutAwayGoodsParams(rid, store_rid, content)

        HttpRequest.sendRequest(HttpRequest.POST, URL.PUT_AWAY_GOODS, params, object : IDataSource.HttpRequestCallBack {
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

}


