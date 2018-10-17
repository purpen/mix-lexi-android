package com.lexivip.lexi.index.discover

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

class DiscoverModel : IDataSource {

    fun getBanner(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        val url = URL.BASE_URL + "banners/discover_ad"
        HttpRequest.sendRequest(HttpRequest.GET, url, params, object : IDataSource.HttpRequestCallBack {
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

    fun getLifeWill(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET, URL.LIFE_WILL, params, object : IDataSource.HttpRequestCallBack {
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

    fun getGuessLike(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["per_page"] = "4"
        HttpRequest.sendRequest(HttpRequest.GET, URL.DISCOVER_GUESS_LIKE, params, object : IDataSource.HttpRequestCallBack {
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

    fun getWonderfulStory(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["per_page"] = "4"
        HttpRequest.sendRequest(HttpRequest.GET, URL.DISCOVER_WONDERFUL_STORY, params, object : IDataSource.HttpRequestCallBack {
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