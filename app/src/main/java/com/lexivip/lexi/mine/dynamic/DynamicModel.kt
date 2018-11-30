package com.lexivip.lexi.mine.dynamic

import android.text.TextUtils
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import com.lexivip.lexi.user.login.UserProfileUtil
import java.io.IOException

open class DynamicModel : IDataSource {

    fun loadData(page: Int, uid: String, callBack: IDataSource.HttpRequestCallBack) {
        val url: String
        val params = ClientParamsAPI.getUserDynamicParams(page)
        if (TextUtils.equals(uid, UserProfileUtil.getUserId())) {
            url = URL.USER_DYNAMIC_URL
        } else {
            params["uid"] = uid
            url = URL.OTHER_USER_DYNAMIC_URL
        }

        HttpRequest.sendRequest(HttpRequest.GET, url, params, object : IDataSource.HttpRequestCallBack {
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

    /**
     * 删除橱窗
     */
    fun deleteShopWindow(rid: String, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["rid"] = rid
        HttpRequest.sendRequest(HttpRequest.DELETE, URL.PUBLISH_SHOP_WINDOW, params, object : IDataSource.HttpRequestCallBack {
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

}