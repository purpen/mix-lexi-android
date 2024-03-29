package com.lexivip.lexi.user.register

import android.text.TextUtils
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.user.LoginWXBean
import com.lexivip.lexi.user.login.UserProfileBean
import com.lexivip.lexi.user.password.VerifyCodeBean
import java.io.IOException


class RegisterPresenter(view: RegisterContract.View) : RegisterContract.Presenter {
    private var view: RegisterContract.View = checkNotNull(view)

    private val dataSource: RegisterModel by lazy { RegisterModel() }

    /**
     * 微信登录绑定手机号
     */
    override fun bindPhoneCode(openid: String, areaCode: String, phone: String, checkCode: String) {
        if (TextUtils.isEmpty(phone)){
            view.showInfo(AppApplication.getContext().getString(R.string.text_phone_null))
            return
        }

        if (TextUtils.isEmpty(checkCode)){
            view.showInfo(AppApplication.getContext().getString(R.string.text_check_code))
            return
        }
        dataSource.bindPhoneCode(openid,areaCode,phone,checkCode,object :IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }
            override fun onSuccess(json: String) {
                LogUtil.e("绑定成功之后："+json)
                view.dismissLoadingView()
                val loginBean = JsonUtil.fromJson(json, LoginWXBean::class.java)
                if (loginBean.success) {
                    SPUtil.write(Constants.AUTHORIZATION, ClientParamsAPI.getAuthorization(loginBean.data.token))
                    getUserProfile()
                } else {
                    view.showInfo(loginBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }

        })
    }
    /**
     * 获取用户信息
     */
    fun getUserProfile() {
        dataSource.getUserProfile(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val userProfileBean = JsonUtil.fromJson(json, UserProfileBean::class.java)
                view.dismissLoadingView()
                if (userProfileBean.success) {
                    SPUtil.write(Constants.USER_PROFILE,json)
                    view.setBindPhoneCode()
                } else {
                    view.showError(userProfileBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 发送验证码
     */
    override fun sendCheckCode(type:Int,areaCode: String,phone: String) {
        if (TextUtils.isEmpty(phone)){
            view.showInfo(AppApplication.getContext().getString(R.string.text_phone_null))
            return
        }

        dataSource.sendCheckCode(type,areaCode,phone,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val verifyCodeBean = JsonUtil.fromJson(json, VerifyCodeBean::class.java)
                if (verifyCodeBean.success) {
                    view.startCountDown()
                    ToastUtil.showSuccess(verifyCodeBean.status.message)
                } else {
                    view.showInfo(verifyCodeBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 验证动态码是否正确
     */
    override fun verifyCheckCode(areaCode:String,phone: String, checkCode: String) {
        if (TextUtils.isEmpty(phone)){
            view.showInfo(AppApplication.getContext().getString(R.string.text_phone_null))
            return
        }

        if (TextUtils.isEmpty(checkCode)){
            view.showInfo(AppApplication.getContext().getString(R.string.text_check_code))
            return
        }

        dataSource.verifyCheckCode(areaCode,phone,checkCode,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val registerBean = JsonUtil.fromJson(json, RegisterBean::class.java)
                if (registerBean.success) {
                    view.goPage(registerBean)
                } else {
                    view.showInfo(registerBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}