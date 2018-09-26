package com.thn.lexi.search

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class SearchBrandModel:IDataSource{

    fun loadData(page:Int,searchString:String,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getSearchBrandPavilionParams(page,searchString)

        HttpRequest.sendRequest(HttpRequest.GET,URL.SEARCH_BRAND_PAVILION_URL,params,object : IDataSource.HttpRequestCallBack{
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