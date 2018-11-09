package com.lexivip.lexi.mine.like.likeShopWindow

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class LikeShopWindowModel {

    fun loadData(page: Int, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["page"] = "$page"
        HttpRequest.sendRequest(HttpRequest.GET, URL.FAVORITE_SHOW_WINDOW, params, object : IDataSource.HttpRequestCallBack {
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

    fun loadData(page: Int,uid:String,callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["page"] = "$page"
        params["uid"] = uid
        HttpRequest.sendRequest(HttpRequest.GET, URL.OTHERS_FAVORITE_SHOW_WINDOW, params, object : IDataSource.HttpRequestCallBack {
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


