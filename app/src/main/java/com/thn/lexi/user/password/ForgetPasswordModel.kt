package com.thn.lexi.user.password

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class ForgetPasswordModel {

    /**
     * 通过手机号换密码
     */
    fun updateNewPassword(phone: String,checkCode:String,password: String,callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getUpdateNewPasswordParams(phone,checkCode,password)

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

}

