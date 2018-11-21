package com.lexivip.lexi.publishShopWindow
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

class PublishShopWindowModel {
    fun publishShopWindow(title: String, content: String, products: ArrayList<ProductBean>, tagList: ArrayList<String>, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getPublishShopWindowParams(title,content,products,tagList)
        HttpRequest.sendRequest(HttpRequest.POST, URL.PUBLISH_SHOP_WINDOW, params, object : IDataSource.HttpRequestCallBack {
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