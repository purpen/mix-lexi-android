package com.lexivip.lexi.user.register

import android.text.TextUtils
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.net.ClientParamsAPI
import java.io.IOException


class SetPasswordPresenter(view: SetPasswordActivity) : SetPasswordContract.Presenter {

    private var view: SetPasswordContract.View = checkNotNull(view)

    private val dataSource: SetPasswordModel by lazy { SetPasswordModel() }

    /**
     * 注册用户
     */
    override fun registerUser(areaCode: String, phone: String, password: String, confirmPassword: String) {

        if (TextUtils.isEmpty(password.trim())) {
            view.showInfo(AppApplication.getContext().getString(R.string.text_password_null))
            return
        }

        if (!TextUtils.equals(password, confirmPassword)) {
            view.showInfo(AppApplication.getContext().getString(R.string.text_password_not_equal))
            return
        }

        dataSource.registerUser(areaCode, phone, password,confirmPassword ,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val setPasswordBean = JsonUtil.fromJson(json, SetPasswordBean::class.java)
                if (setPasswordBean.success) {
                    val authorization = ClientParamsAPI.getAuthorization(setPasswordBean.data.token)
                    SPUtil.write(Constants.AUTHORIZATION,authorization)
                    view.goPage()
                } else {
                    view.showInfo(setPasswordBean.status.message)
                }

            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }



}