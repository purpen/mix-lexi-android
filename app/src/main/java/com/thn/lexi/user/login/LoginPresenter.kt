package com.thn.lexi.user.login

import com.basemodule.tools.Constants
import com.basemodule.tools.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.SPUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.user.register.TokenBean
import java.io.IOException

class LoginPresenter : com.thn.lexi.user.login.LoginContract.Presenter {

    private var view: com.thn.lexi.user.login.LoginContract.View
    private val dataSource: LoginModel by lazy { LoginModel() }

    constructor(view: com.thn.lexi.user.login.LoginContract.View){
        this.view = checkNotNull(view)
    }

    override fun loginUser(phone: String, password: String) {
        dataSource.loginUser(phone,password,object:IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val fromJson = JsonUtil.fromJson(json, LoginBean::class.java)
                if (fromJson.success){
                    getToken(phone,password)
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
}