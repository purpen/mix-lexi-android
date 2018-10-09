package com.thn.lexi.index.explore.collection
import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class CollectionDetailModel{

    fun loadData(id:String,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDefaultParams()
        params["id"] = id

        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_COLLECTION_DETAIL, params, object : IDataSource.HttpRequestCallBack {
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


