package com.lexivip.lexi.selectionGoodsCenter

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class OfficialRecommendModel{

    fun loadData(page: Int, callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getHotGoodsParams(page)

        HttpRequest.sendRequest(HttpRequest.GET,URL.OFFICIAL_RECOMMEND_URL,params,object : IDataSource.HttpRequestCallBack{
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


