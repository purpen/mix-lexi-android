package com.lexivip.lexi.welcome

import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

class WelcomeModel{
    fun bindWX(map: Map<String,String>,callback: IDataSource.HttpRequestCallBack){
        val params= ClientParamsAPI.getBindWX(map)
        HttpRequest.sendRequest(HttpRequest.POST, URL.WX_LOGIN,params,object : IDataSource.HttpRequestCallBack{
            override fun onStart() {
                callback.onStart()
            }

            override fun onSuccess(json: String) {
                callback.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callback.onFailure(e)
            }
        })
    }
    fun getUserProfile(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET, URL.USER_PROFILE_URL,params, object : IDataSource.HttpRequestCallBack {
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