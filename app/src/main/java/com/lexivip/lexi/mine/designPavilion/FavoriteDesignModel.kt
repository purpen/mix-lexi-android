package com.lexivip.lexi.mine.designPavilion

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class FavoriteDesignModel:IDataSource{

    fun loadData(page:Int,callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFocusedDesignPavilionParams(page)

        HttpRequest.sendRequest(HttpRequest.GET,URL.FOCUSED_BRAND_PAVILION_URL,params,object : IDataSource.HttpRequestCallBack{
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

    fun loadData(page:Int,uid:String,callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFocusedDesignPavilionParams(page)
        params["uid"] = uid
        HttpRequest.sendRequest(HttpRequest.GET,URL.OTHER_FOCUSED_BRAND_PAVILION,params,object : IDataSource.HttpRequestCallBack{
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

    fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, httpRequestCallBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getFocusBrandPavilionParams(store_rid)

        val url: String
        if (isFavorite) {
            url = URL.FOCUS_BRAND_PAVILION
        } else {
            url = URL.UNFOCUS_BRAND_PAVILION
        }
        HttpRequest.sendRequest(HttpRequest.POST, url, params, object : IDataSource.HttpRequestCallBack {
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