package com.thn.lexi.user.register

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class RegisterModel {

    fun registerUser(phone: String, password: String,checkCode:String,callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getRegisterParams(phone,password,checkCode)

        HttpRequest.sendRequest(HttpRequest.POST,URL.REGISTER_URL,params, object : IDataSource.HttpRequestCallBack {
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


    /**
     * 发送动态码
     */
    fun sendCheckCode(phone: String,callback: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getCheckCodeRequestParams(phone)
        HttpRequest.sendRequest(HttpRequest.POST,URL.REGISTER_URL,params, object : IDataSource.HttpRequestCallBack {
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

    /**
     * 验证动态码是否正确
     */
    fun verifyCheckCode(phone: String, checkCode: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.verifyCheckCodeParams(phone,checkCode)
        HttpRequest.sendRequest(HttpRequest.POST,URL.REGISTER_URL,params, object : IDataSource.HttpRequestCallBack {
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

