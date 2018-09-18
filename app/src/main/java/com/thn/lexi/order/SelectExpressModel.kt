package com.thn.lexi.order
import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class SelectExpressModel{

    fun loadData(expressModelId:String,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getExpressListByExpressModel(expressModelId)

        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_FAVORITE_USERS, params, object : IDataSource.HttpRequestCallBack {
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


