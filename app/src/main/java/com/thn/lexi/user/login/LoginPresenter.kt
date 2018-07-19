package com.thn.lexi.user.login

import android.util.Base64
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.user.register.TokenBean
import java.io.IOException
import java.util.HashMap

class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View
    private val dataSource: LoginModel by lazy { LoginModel() }

    constructor(view: LoginContract.View){
        this.view = checkNotNull(view)
    }

    //暂时用商户身份登录
    override fun loginUser(phone: String, password: String) {
        val authorzationCode = getTempAuthorzationCode(phone, password)
        dataSource.loginUser(phone,password,authorzationCode,object:IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val fromJson = JsonUtil.fromJson(json, LoginBean::class.java)
                if (fromJson.success){
//                    getToken(phone,password)
                    getAppKeyAndSecret(fromJson.data.store_rid,authorzationCode)
                }else{
                    view.showError(fromJson.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    // 获取商户的key和token
    private fun getAppKeyAndSecret(storeId: String, authorzationCode: String) {

        dataSource.getAppKeyAndSecret(storeId,authorzationCode,object:IDataSource.HttpRequestCallBack{
            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val appKeyData = JsonUtil.fromJson(json, AppKeyData::class.java)
                if (appKeyData.success) {
                    SPUtil.write(Constants.AUTHORIZATION, authorzationCode)
                    SPUtil.write(Constants.APP_KEY, appKeyData.data.app_key)
                    SPUtil.write(Constants.APP_SECRET, appKeyData.data.access_token)
                    //跳转主页
                    view.goPage()
                } else {
                    view.showError(appKeyData.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取token
     */
    private fun getToken(phone: String, password: String) {
        dataSource.getToken(phone, password, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                LogUtil.e(json)
                val tokenBean = JsonUtil.fromJson(json, TokenBean::class.java)
                if (tokenBean.success) {
                    SPUtil.write(Constants.AUTHORIZATION,tokenBean.data.token)
                    view.goPage()
                } else {
                    view.showInfo(tokenBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showInfo(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    override fun wechatLogin() {
        dataSource.weChatLogin()
    }

    override fun qqLogin() {
        dataSource.qqLogin()
    }

    override fun sinaLogin() {
        dataSource.sinaLogin()
    }

    private fun getTempAuthorzationCode(phone: String,password: String): String {
        var str = "$phone:$password"
        str = "Basic  " + Base64.encodeToString(str.toByteArray(), Base64.DEFAULT)
        return str.trim()
    }

    fun getCheckCode(phone: String) {
        ToastUtil.showInfo("发送验证码")
    }
}