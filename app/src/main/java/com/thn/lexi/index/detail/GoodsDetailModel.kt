package com.thn.lexi.index.detail

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

class GoodsDetailModel:IDataSource {

    fun loadData(goodsId: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        HttpRequest.sendRequest(HttpRequest.GET,URL.BASE_URL+"products/$goodsId/all_detail",object :IDataSource.HttpRequestCallBack{
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

    fun loadGoodsInfo(goodsId: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        HttpRequest.sendRequest(HttpRequest.GET,URL.BASE_URL+"products/$goodsId",object :IDataSource.HttpRequestCallBack{
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