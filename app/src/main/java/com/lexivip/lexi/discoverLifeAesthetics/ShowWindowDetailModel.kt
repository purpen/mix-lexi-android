package com.lexivip.lexi.discoverLifeAesthetics
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class ShowWindowDetailModel{

    fun loadData(rid: String,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getShowWindowDetailParams(rid)

        HttpRequest.sendRequest(HttpRequest.GET,URL.SHOW_WINDOW_DETAIL,params,object : IDataSource.HttpRequestCallBack{
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

    fun favoriteShowWindow(rid: String,isFavorite:Boolean,httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFavoriteShowWindowParams(rid)
        var method=""
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


    fun getGuessLike(rid: String,httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getGuessLikeParams(rid)
        HttpRequest.sendRequest(HttpRequest.GET, URL.SHOP_WINDOWS_GUESS_LIKE, params, object : IDataSource.HttpRequestCallBack {
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

    fun getRelateShowWindow(rid: String,httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getRelateShowWindowParams(rid)
        HttpRequest.sendRequest(HttpRequest.GET, URL.SHOP_WINDOWS_SIMILAR, params, object : IDataSource.HttpRequestCallBack {
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

    fun focusUser(uid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFocusUserParams(uid)
        HttpRequest.sendRequest(HttpRequest.POST, URL.FOCUS_USER_URL, params, object : IDataSource.HttpRequestCallBack {
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

    fun unfocusUser(uid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFocusUserParams(uid)
        HttpRequest.sendRequest(HttpRequest.POST, URL.UNFOCUS_USER_URL, params, object : IDataSource.HttpRequestCallBack {
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


    fun praiseComment(comment_id: String,isPraise:Boolean,httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getPraiseCommentParams(comment_id)
        val method:String
        if (isPraise){
            method = HttpRequest.DELETE
        }else{
            method = HttpRequest.POST
        }
        HttpRequest.sendRequest(method, URL.SHOP_WINDOWS_COMMENTS_PRAISE, params, object : IDataSource.HttpRequestCallBack {
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


    fun loadMoreSubComments(page: Int,comment_id: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getMoreSubCommentsParams(page,comment_id)
        HttpRequest.sendRequest(HttpRequest.GET, URL.SHOP_WINDOWS_SUB_COMMENTS, params, object : IDataSource.HttpRequestCallBack {
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

    fun submitComment(rid: String, pid: String,replyId:String,content: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getSubmitCommentsParams(rid,pid,replyId,content)
        HttpRequest.sendRequest(HttpRequest.POST, URL.SHOW_WINDOW_COMMENTS_LIST, params, object : IDataSource.HttpRequestCallBack {
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


