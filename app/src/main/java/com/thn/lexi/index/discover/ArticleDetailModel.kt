package com.thn.lexi.index.discover
import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

class ArticleDetailModel : IDataSource {

    fun loadData(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["rid"] = rid

        HttpRequest.sendRequest(HttpRequest.GET, URL.LIFE_RECORDS_ARTICLE_DETAIL, params, object : IDataSource.HttpRequestCallBack {
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

    fun focusUser(uid: String, isFollow: Boolean, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFocusUserParams(uid)
        val url:String

        if (isFollow){ //取消关注
            url = URL.UNFOCUS_USER_URL
        }else{
            url = URL.FOCUS_USER_URL
        }

        HttpRequest.sendRequest(HttpRequest.POST, url, params, object : IDataSource.HttpRequestCallBack {
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