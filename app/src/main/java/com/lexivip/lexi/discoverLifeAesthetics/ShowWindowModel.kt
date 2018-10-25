package com.lexivip.lexi.discoverLifeAesthetics

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class ShowWindowModel{

    fun loadData(page: Int,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getShowWindowParams(page)

        HttpRequest.sendRequest(HttpRequest.GET,URL.RECOMMEND_SHOW_WINDOW,params,object : IDataSource.HttpRequestCallBack{
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

    fun favoriteShowWindow(rid: String,isFavorite:Boolean ,httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFavoriteShowWindowParams(rid)
        var method =""
        if (isFavorite){
            method = HttpRequest.DELETE
        }else{
            method = HttpRequest.POST
        }
        HttpRequest.sendRequest(method, URL.FAVORITE_SHOW_WINDOW,params,object : IDataSource.HttpRequestCallBack{
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

    /**
     * 加载关注的橱窗
     */
    fun loadFocusData(page: Int, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getShowWindowParams(page)

        HttpRequest.sendRequest(HttpRequest.GET,URL.FOCUS_SHOW_WINDOW,params,object : IDataSource.HttpRequestCallBack{
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


    fun focusUser(uid: String, isFollowed: Boolean, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFocusUserParams(uid)
        val url:String

        if (isFollowed){
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


