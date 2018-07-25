package com.thn.lexi.user.register

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

class SetPasswordModel {
    fun registerUser(areaCode:String,phone: String, password:String,callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getRegisterParams(areaCode,phone,password)

        HttpRequest.sendRequest(HttpRequest.POST, URL.REGISTER_URL,params, object : IDataSource.HttpRequestCallBack {
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

    fun getToken(phone: String, password: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getTokenParams(phone,password)

        HttpRequest.sendRequest(HttpRequest.POST,URL.TOKEN_URL,params, object : IDataSource.HttpRequestCallBack {
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
