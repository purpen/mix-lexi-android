package com.lexivip.lexi.index.discover
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
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

    fun getRelateStories(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["rid"] = rid
        params["per_page"] = "4"
        HttpRequest.sendRequest(HttpRequest.GET, URL.LIFE_RECORDS_SIMILAR, params, object : IDataSource.HttpRequestCallBack {
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

    fun getRecommendProducts(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["rid"] = rid
        HttpRequest.sendRequest(HttpRequest.GET, URL.LIFE_RECORDS_RECOMMEND_PRODUCTS, params, object : IDataSource.HttpRequestCallBack {
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

    fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, httpRequestCallBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getFocusBrandPavilionParams(store_rid)

        val url: String
        if (isFavorite) {
            url = URL.FOCUS_BRAND_PAVILION
        } else {
            url = URL.UNFOCUS_BRAND_PAVILION
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