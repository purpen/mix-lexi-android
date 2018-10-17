package com.lexivip.lexi.index.detail
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class FavoriteUserListModel{

    fun loadData(goodsId:String,page: Int,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getFavoriteUsers(goodsId,page)
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_FAVORITE_USERS, params, object : IDataSource.HttpRequestCallBack {
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

    fun focusUser(uid: String, focusState: Int, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFocusUserParams(uid)
        val url:String

        if (focusState==0){
            url = URL.FOCUS_USER_URL
        }else{
            url = URL.UNFOCUS_USER_URL
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


