package com.lexivip.lexi.index.selection.applyForLifeHouse

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class ApplyForLifeHouseModel {
    open fun applyForLifeHouse(name: String, job: String, countryCode: String, phone: String, checkCode: String, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getApplyForLifeHouseParams(name, job, countryCode, phone, checkCode)
        HttpRequest.sendRequest(HttpRequest.POST, URL.APPLY_LIFE_HOUSE, params, object : IDataSource.HttpRequestCallBack {
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

    /**
     * 发送验证码
     */
    fun sendCheckCode(areaCode: String, phone: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getCheckCodeRequestParams(areaCode, phone)
        HttpRequest.sendRequest(HttpRequest.POST, URL.LOGIN_SEND_CHECKCODE, params, object : IDataSource.HttpRequestCallBack {
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