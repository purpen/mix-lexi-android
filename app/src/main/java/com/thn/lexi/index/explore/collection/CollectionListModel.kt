package com.thn.lexi.index.explore.collection
import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
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


