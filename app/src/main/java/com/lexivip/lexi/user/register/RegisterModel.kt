package com.lexivip.lexi.user.register

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class RegisterModel {

    /**
     * 发送动态码
     */
    fun sendCheckCode(areaCode:String,phone: String,callback: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getCheckCodeRequestParams(areaCode,phone)
        HttpRequest.sendRequest(HttpRequest.POST,URL.REGISTER_VERIFY_CODE,params, object : IDataSource.HttpRequestCallBack {
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
     * 验证动态码是否正确
     */
    fun verifyCheckCode(areaCode:String,phone: String, checkCode: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.verifyCheckCodeParams(areaCode,phone,checkCode)
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

