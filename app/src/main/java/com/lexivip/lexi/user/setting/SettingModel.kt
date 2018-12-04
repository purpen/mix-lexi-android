package com.lexivip.lexi.user.setting

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class SettingModel {

    fun loadData(callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDefaultParams()

        HttpRequest.sendRequest(HttpRequest.GET,URL.USER_CENTER,params, object : IDataSource.HttpRequestCallBack {
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
    fun bindWX(openid: String,callback: IDataSource.HttpRequestCallBack){
        val params=ClientParamsAPI.getDefaultParams()
        /*HttpRequest.sendRequest(HttpRequest.POST,URL.BIND_WX,params,object :IDataSource.HttpRequestCallBack{
            override fun onStart() {
                callback.onStart()
            }

            override fun onSuccess(json: String) {
                callback.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callback.onFailure(e)
            }
        })*/
    }
}

