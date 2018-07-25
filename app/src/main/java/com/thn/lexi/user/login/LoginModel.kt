package com.thn.lexi.user.login

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class LoginModel{
    open fun loginUser(phone: String, password: String,authorzationCode: String,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getLoginParams(phone,password)

        HttpRequest.sendRequest(HttpRequest.POST,URL.LOGIN_URL,authorzationCode,params,object : IDataSource.HttpRequestCallBack{
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

    open fun weChatLogin(){

    }

    open fun qqLogin(){

    }

    open fun sinaLogin(){

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


    fun getAppKeyAndSecret(storeId: String, authorzationCode: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.appKeyAndSecretParams(storeId)
        HttpRequest.sendRequest(HttpRequest.POST,URL.APPKEY_APPSECRET,authorzationCode,params, object : IDataSource.HttpRequestCallBack {
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
     * 登录发送验证码
     */
    fun sendCheckCode(areaCode: String, phone: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getCheckCodeRequestParams(areaCode,phone)
        HttpRequest.sendRequest(HttpRequest.POST,URL.REGISTER_VERIFY_CODE,params, object : IDataSource.HttpRequestCallBack {
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