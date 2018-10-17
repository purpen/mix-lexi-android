package com.lexivip.lexi.user.password
import android.text.TextUtils
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import java.io.IOException

class ForgetPasswordPresenter(view: ForgetPasswordContract.View) : ForgetPasswordContract.Presenter {

    private var view: ForgetPasswordContract.View = checkNotNull(view)

    private val dataSource: ForgetPasswordModel by lazy { ForgetPasswordModel() }

    /**
     * 发送验证码
     */
    fun sendCheckCode(areaCode: String,phone: String) {

        if (TextUtils.isEmpty(phone)){
            view.showInfo(AppApplication.getContext().getString(R.string.text_phone_null))
            return
        }

        dataSource.sendCheckCode(areaCode,phone,object : IDataSource.HttpRequestCallBack {
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
     * 验证动态码
     */
    fun verifyCheckCode(areaCode:String,phone: String, checkCode: String) {

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
                val forgetPasswordBean = JsonUtil.fromJson(json, ForgetPasswordBean::class.java)
                if (forgetPasswordBean.success) {
                    view.goPage(forgetPasswordBean.data.email)
                } else {
                    view.showInfo(forgetPasswordBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}