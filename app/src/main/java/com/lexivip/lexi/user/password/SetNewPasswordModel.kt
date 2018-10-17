package com.lexivip.lexi.user.password

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class SetNewPasswordModel {

    /**
     *  忘记密码后设置密码
     */
    fun updateNewPassword(phone:String,password: String,confirmPassword: String,callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getUpdatePasswordParams(phone,password,confirmPassword)

        HttpRequest.sendRequest(HttpRequest.POST,URL.FORGET_PASSWORD_SET_NEW,params, object : IDataSource.HttpRequestCallBack {
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

}

