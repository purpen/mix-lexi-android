package com.thn.lexi.user.login
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.user.password.VerifyCodeBean
import java.io.IOException

class LoginPresenter(view: LoginContract.View) : LoginContract.Presenter {

    private var view: LoginContract.View = checkNotNull(view)
    private val dataSource: LoginModel by lazy { LoginModel() }


    /**
     * 登录
     */
    override fun loginUser(phone: String, password: String) {
        val authorization = ClientParamsAPI.getAuthorization(phone, password)
        dataSource.loginUser(phone, password, authorization, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val loginBean = JsonUtil.fromJson(json, LoginBean::class.java)
                if (loginBean.success) {
                    SPUtil.write(Constants.AUTHORIZATION,ClientParamsAPI.getAuthorization(loginBean.data.token))
                    SPUtil.write(Constants.LOGIN_BEAN,json)
                    view.goPage()
                } else {
                    view.showError(loginBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
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

    override fun sendCheckCode(areaCode: String, phone: String) {
        dataSource.sendCheckCode(areaCode, phone, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val verifyCodeBean = JsonUtil.fromJson(json, VerifyCodeBean::class.java)
                if (verifyCodeBean.success) {
                    ToastUtil.showSuccess(verifyCodeBean.status.message)
                } else {
                    view.showInfo(verifyCodeBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 通过手机号和动态码登录
     */
    fun loginUserWithCheckCode(areaCode: String, phone: String, checkCode: String) {
        dataSource.loginUserWithCheckCode(areaCode, phone,checkCode,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val loginBean = JsonUtil.fromJson(json, LoginBean::class.java)
                if (loginBean.success) {
                    val authorization = ClientParamsAPI.getAuthorization(loginBean.data.token)
                    SPUtil.write(Constants.AUTHORIZATION,authorization)
                    SPUtil.write(Constants.LOGIN_BEAN,json)
                    view.goPage(loginBean.data.is_first_login)
                } else {
                    view.showInfo(loginBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}