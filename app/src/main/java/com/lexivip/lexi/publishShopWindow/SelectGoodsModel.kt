package com.lexivip.lexi.publishShopWindow
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

class SelectGoodsModel {
    fun loadData(whichPage: Int, page: Int, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["page"] = "$page"
        params["per_page"] = "9"
        var url: String = URL.FAVORITE_GOODS_URL
        when (whichPage) {
            0 -> {
                url = URL.FAVORITE_GOODS_URL
            }
            1 -> {
                url = URL.WISH_ORDER
            }
            2 -> {
                url = URL.RECENT_LOOK_GOODS
            }
        }

        HttpRequest.sendRequest(HttpRequest.GET, url, params, object : IDataSource.HttpRequestCallBack {
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

    fun loadGoodsImageById(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["rid"] = rid
        HttpRequest.sendRequest(HttpRequest.GET, URL.SELECT_GOODS_IMAGE, params, object : IDataSource.HttpRequestCallBack {
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