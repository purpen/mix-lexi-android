package com.thn.lexi.mine

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class FavoriteDesignModel:IDataSource{

    //TODO 改为FOLLOWED_STORES
    fun loadData(page:Int,callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFocusedDesignPavilionParams(page)

        HttpRequest.sendRequest(HttpRequest.GET,URL.BRAND_PAVILION_URL,params,object : IDataSource.HttpRequestCallBack{
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