package com.lexivip.lexi.mine

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class MineModel : IDataSource {

    fun loadData(callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()

        HttpRequest.sendRequest(HttpRequest.GET, URL.USER_CENTER, params, object : IDataSource.HttpRequestCallBack {
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

    fun loadData(uid: String, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["uid"] = uid
        HttpRequest.sendRequest(HttpRequest.GET, URL.OTHERS_USER_CENTER, params, object : IDataSource.HttpRequestCallBack {
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