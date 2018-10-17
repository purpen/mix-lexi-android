package com.lexivip.lexi.index.explore.collection
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class CollectionListModel{

    fun loadData(page: Int,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDefaultParams()
        params["page"] = "$page"

        HttpRequest.sendRequest(HttpRequest.GET, URL.WELL_GOODS_COLLECTION, params, object : IDataSource.HttpRequestCallBack {
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


