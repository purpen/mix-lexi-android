package com.thn.lexi.user.register

import android.text.TextUtils
import com.basemodule.tools.Constants
import com.basemodule.tools.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.SPUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.user.password.ForgetPasswordBean
import java.io.IOException


class RegisterPresenter(view: RegisterContract.View) : RegisterContract.Presenter {

    private var view: RegisterContract.View = checkNotNull(view)

    private val dataSource: RegisterModel by lazy { RegisterModel() }

    /**
     * 注册用户
     */
    override fun registerUser(phone: String, password: String, checkCode: String) {

        if (TextUtils.isEmpty(phone.trim())) {
            view.showInfo(AppApplication.getContext().getString(R.string.text_phone_null))
            return
        }

        if (TextUtils.isEmpty(password.trim())) {
            view.showInfo(AppApplication.getContext().getString(R.string.text_password_null))
            return
        }

        dataSource.registerUser(phone, password, checkCode, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val registerBean = JsonUtil.fromJson(json, ForgetPasswordBean::class.java)
                if (registerBean.success) {
                    getToken(phone, password)
                } else {
                    view.showInfo(registerBean.status.message)
                }


            }

            override fun onFailure(e: IOException) {
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


    fun sendCheckCode() {
        dataSource.sendCheckCode()
    }

}