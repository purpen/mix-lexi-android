package com.lexivip.lexi.index.explore.collection
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class CollectionDetailModel{

    fun loadData(id:String,page:Int,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDefaultParams()
        params["id"] = id
        params["page"] = "$page"

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


