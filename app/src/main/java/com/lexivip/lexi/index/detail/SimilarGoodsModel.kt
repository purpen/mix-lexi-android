package com.lexivip.lexi.index.detail

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

class SimilarGoodsModel:IDataSource {
    fun loadData(page:Int,callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getSimilarParams(page)
        HttpRequest.sendRequest(HttpRequest.GET, URL.SIMILAR_GOODS_URL,params,object : IDataSource.HttpRequestCallBack{
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