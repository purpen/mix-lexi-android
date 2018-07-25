package com.thn.lexi.user.completeinfo

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class CompleteInfoModel {

    /**
     * 通过手机号换密码
     */
    fun updateNewPassword(password: String,callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getUpdateNewPasswordParams(password)

        HttpRequest.sendRequest(HttpRequest.POST,URL.REGISTER_URL,params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callback.onStart()
            }

            override fun onSuccess(json: String) {
                callback.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callback.onFailure(e)
            }
        })
    }

    //发送验证码
    fun sendCheckCode() {

    }

    /**
     * 获取上传token
     */
    fun uploadAvatar(callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDefaultParams()

        HttpRequest.sendRequest(HttpRequest.GET,URL.UPLOAD_TOKEN,params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callback.onStart()
            }

            override fun onSuccess(json: String) {
                callback.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callback.onFailure(e)
            }
        })
    }

    fun uploadUserInfo(avatar_id: String, name: String, birth: String, gender: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()

        HttpRequest.sendRequest(HttpRequest.GET,URL.COMPLETE_USER_INFO,params, object : IDataSource.HttpRequestCallBack {
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

