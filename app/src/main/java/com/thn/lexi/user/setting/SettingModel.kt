package com.thn.lexi.user.setting

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class SettingModel {

    fun loadData(userId:String,callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getSettingParams(userId)

        HttpRequest.sendRequest(HttpRequest.POST,URL.USER_INFO_URL,params, object : IDataSource.HttpRequestCallBack {
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
}

