package com.thn.lexi.user.password

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class ForgetPasswordModel {

    //发送验证码
    fun sendCheckCode(areaCode: String,phone: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getCheckCodeRequestParams(areaCode,phone)
        HttpRequest.sendRequest(HttpRequest.POST,URL.FORGET_PASSWORD_SEND_CHECKCODE,params, object : IDataSource.HttpRequestCallBack {
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


    fun verifyCheckCode(areaCode:String,phone: String, checkCode: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.verifyCheckCodeParams(areaCode,phone,checkCode)
        HttpRequest.sendRequest(HttpRequest.POST,URL.FORGET_PASSWORD_VERIFY_CHECKCODE,params, object : IDataSource.HttpRequestCallBack {
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

