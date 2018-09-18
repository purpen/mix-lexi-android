package com.thn.lexi.order
import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class SelectExpressModel{

    fun loadData(selectExpressRequestBean: SelectExpressRequestBean,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getExpressListByExpressModel(selectExpressRequestBean)

        HttpRequest.sendRequest(HttpRequest.POST, URL.SAME_TEMPLATE_EXPRESS, params, object : IDataSource.HttpRequestCallBack {
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


