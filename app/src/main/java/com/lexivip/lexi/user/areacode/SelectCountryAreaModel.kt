package com.lexivip.lexi.user.areacode

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

class SelectCountryAreaModel {
    fun loadData(page: Int, status: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getAreaCodes(page,status)
        HttpRequest.sendRequest(HttpRequest.GET,URL.AREA_CODE_URL,params,object :IDataSource.HttpRequestCallBack{
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