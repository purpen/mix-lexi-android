package com.thn.lexi.user.password

import android.text.TextUtils
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class SetNewPasswordPresenter(view: SetNewPasswordContract.View) : SetNewPasswordContract.Presenter {

    private var view: SetNewPasswordContract.View = checkNotNull(view)

    private val dataSource: SetNewPasswordModel by lazy { SetNewPasswordModel() }

    /**
     * 通过手机号更换新密码
     */
    override fun updateNewPassword(password: String) {

//        if (TextUtils.isEmpty(phone.trim())) {
//            view.showInfo(AppApplication.getContext().getString(R.string.text_phone_null))
//            return
//        }
//
//        if (TextUtils.isEmpty(checkCode.trim())) {
//            view.showInfo(AppApplication.getContext().getString(R.string.text_check_code))
//            return
//        }

        if (TextUtils.isEmpty(password.trim())) {
            view.showInfo(AppApplication.getContext().getString(R.string.tex_hint_new_password))
            return
        }


        dataSource.updateNewPassword(password, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val forgetPasswordBean = JsonUtil.fromJson(json, ForgetPasswordBean::class.java)
                if (forgetPasswordBean.success) {
                    ToastUtil.showSuccess(forgetPasswordBean.status.message)
                } else {
                    view.showInfo(forgetPasswordBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    fun sendCheckCode() {
        dataSource.sendCheckCode()
    }

}