package com.lexivip.lexi.mine.enshrine

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class EnshrineModel:IDataSource{

    fun getUserRecentLook(callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()

        HttpRequest.sendRequest(HttpRequest.GET,URL.RECENT_LOOK_GOODS,params,object : IDataSource.HttpRequestCallBack{
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

    fun getWishOrder(callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()

        HttpRequest.sendRequest(HttpRequest.GET,URL.WISH_ORDER,params,object : IDataSource.HttpRequestCallBack{
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

    fun getOtherUserWishOrder(uid: String, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["uid"] = uid
        HttpRequest.sendRequest(HttpRequest.GET,URL.OTHERS_USER_WISHLIST,params,object : IDataSource.HttpRequestCallBack{
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


    fun getOtherUserRecentLook(uid: String, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["uid"] = uid
        HttpRequest.sendRequest(HttpRequest.GET,URL.OTHER_RECENT_LOOK_GOODS,params,object : IDataSource.HttpRequestCallBack{
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