package com.lexivip.lexi.brandPavilion

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class FeatureBrandPavilionModel{

    fun loadData(page: Int,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getShowWindowParams(page)

        HttpRequest.sendRequest(HttpRequest.GET,URL.ALL_FEATURE_BRAND_PAVILION,params,object : IDataSource.HttpRequestCallBack{
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


