package com.lexivip.lexi.index.detail
import com.basemodule.tools.LogUtil
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

    fun loadOtherData(uid: String,page: Int,type: Int,callBack: IDataSource.HttpRequestCallBack) {
        LogUtil.e("第几页："+page)
        var params=ClientParamsAPI.getOhterUsers(uid,page)
        var url: String?
        if (3==type){
            url=URL.OTHER_USER_FOLLOW
        }else{
            url=URL.OTHER_USER_FANS
        }
        HttpRequest.sendRequest(HttpRequest.GET,url,params,object :IDataSource.HttpRequestCallBack{
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

    fun loadUserData(page: Int,type: Int,callBack: IDataSource.HttpRequestCallBack) {
        LogUtil.e("第几页："+page)
        var params=ClientParamsAPI.getUserFollow(page)
        var url: String?
        if (1==type){
            url=URL.USER_FOLLOW
        }else{
            url=URL.USER_FANS
        }
        HttpRequest.sendRequest(HttpRequest.GET,url,params,object :IDataSource.HttpRequestCallBack{
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
        val url:String?

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


