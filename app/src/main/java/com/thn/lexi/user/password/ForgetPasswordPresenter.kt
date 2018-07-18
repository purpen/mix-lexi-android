package com.thn.lexi.user.password
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class ForgetPasswordPresenter(view: ForgetPasswordContract.View) : ForgetPasswordContract.Presenter {

    private var view: ForgetPasswordContract.View = checkNotNull(view)

    private val dataSource: ForgetPasswordModel by lazy { ForgetPasswordModel() }

    /**
     * 发送验证码
     */
    fun sendCheckCode(phone: String) {
        dataSource.sendCheckCode(phone,object : IDataSource.HttpRequestCallBack {
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


    /**
     * 验证动态码
     */
    fun verifyCheckCode(phone: String, checkCode: String) {
        dataSource.verifyCheckCode(phone,checkCode,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {

                //TODO 验证码Bean
                LogUtil.e(json)
                view.dismissLoadingView()
                val forgetPasswordBean = JsonUtil.fromJson(json, ForgetPasswordBean::class.java)
                if (forgetPasswordBean.success) {
                    view.goPage()
                } else {
                    view.showInfo(forgetPasswordBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}