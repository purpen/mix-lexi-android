package com.lexivip.lexi.user.login
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.user.password.VerifyCodeBean
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
                LogUtil.e(json)
                view.dismissLoadingView()
                val loginBean = JsonUtil.fromJson(json, LoginBean::class.java)
                if (loginBean.success) {
                    SPUtil.write(Constants.AUTHORIZATION,ClientParamsAPI.getAuthorization(loginBean.data.token))
                    getUserProfile(loginBean.data.is_first_login)
                } else {
                    view.showError(loginBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showHint()
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
                view.dismissLoadingView()
                val verifyCodeBean = JsonUtil.fromJson(json, VerifyCodeBean::class.java)
                if (verifyCodeBean.success) {
                    view.startCountDown()
                    ToastUtil.showSuccess(AppApplication.getContext().getString(R.string.text_check_code_sended))
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
                    getUserProfile(loginBean.data.is_first_login)
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

    /**
     * 获取用户信息
     */
    fun getUserProfile(isFirstLogin: Boolean) {
        dataSource.getUserProfile(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val userProfileBean = JsonUtil.fromJson(json, UserProfileBean::class.java)
                if (userProfileBean.success) {
                    SPUtil.write(Constants.USER_PROFILE,json)
                    view.goPage(isFirstLogin)
                } else {
                    view.showInfo(userProfileBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}