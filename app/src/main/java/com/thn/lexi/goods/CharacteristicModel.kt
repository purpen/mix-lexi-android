package com.thn.lexi.goods

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class CharacteristicModel:IDataSource{


    override fun loadData(cid: String, page: Int, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getGoodListParams(cid,page)

        HttpRequest.sendRequest(HttpRequest.GET,URL.GOODS_LIST_URL,params,object : IDataSource.HttpRequestCallBack{
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