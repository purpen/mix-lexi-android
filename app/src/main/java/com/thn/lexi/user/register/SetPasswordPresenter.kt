package com.thn.lexi.user.register

import android.text.TextUtils
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.user.password.ForgetPasswordBean
import java.io.IOException


class SetPasswordPresenter(view: SetPasswordActivity) : SetPasswordContract.Presenter {

    private var view: SetPasswordContract.View = checkNotNull(view)

    private val dataSource: SetPasswordModel by lazy { SetPasswordModel() }

    /**
     * 注册用户
     */
    override fun registerUser(areaCode: String, phone: String, password: String, confirmPassword: String) {
        if (!TextUtils.equals(password, confirmPassword)) {
            view.showInfo(AppApplication.getContext().getString(R.string.text_password_not_equal))
            return
        }

        if (TextUtils.isEmpty(password.trim())) {
            view.showInfo(AppApplication.getContext().getString(R.string.text_password_null))
            return
        }

        dataSource.registerUser(areaCode, phone, password, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val setPasswordBean = JsonUtil.fromJson(json, SetPasswordBean::class.java)
                if (setPasswordBean.success) {
                    getToken(phone, password)
                } else {
                    view.showInfo(setPasswordBean.status.message)
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
                    SPUtil.write(Constants.AUTHORIZATION, tokenBean.data.token)
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

}