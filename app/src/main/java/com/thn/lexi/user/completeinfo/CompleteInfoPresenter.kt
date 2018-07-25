package com.thn.lexi.user.completeinfo

import com.basemodule.tools.JsonUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class CompleteInfoPresenter(view: CompleteInfoContract.View) : CompleteInfoContract.Presenter {

    private var view: CompleteInfoContract.View = checkNotNull(view)

    private val dataSource: CompleteInfoModel by lazy { CompleteInfoModel() }

    override fun uploadAvatar() {
        dataSource.uploadAvatar(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
            }

            override fun onSuccess(json: String) {
                val uploadTokenBean = JsonUtil.fromJson(json, UploadTokenBean::class.java)
                if (uploadTokenBean.success) {
                    realUploadAvatar()
                } else {
                    view.showInfo(uploadTokenBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    private fun realUploadAvatar() {

    }


    /**
     * 首次登录完善用户资料
     */
    override fun uploadUserInfo(avatar_id: String, name: String, birth: String, gender: String) {
        dataSource.uploadUserInfo(avatar_id, name, birth, gender, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val completeInfoBean = JsonUtil.fromJson(json, CompleteInfoBean::class.java)
                if (completeInfoBean.success) {
                    ToastUtil.showSuccess(completeInfoBean.status.message)
                } else {
                    view.showInfo(completeInfoBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
    /**
     * 发送验证码
     */
//    fun sendCheckCode(phone: String) {
//        dataSource.sendCheckCode(phone,object : IDataSource.HttpRequestCallBack {
//            override fun onStart() {
//                view.showLoadingView()
//            }
//
//            override fun onSuccess(json: String) {
//                LogUtil.e(json)
//                view.dismissLoadingView()
//                val forgetPasswordBean = JsonUtil.fromJson(json, ForgetPasswordBean::class.java)
//                if (forgetPasswordBean.success) {
//                    ToastUtil.showSuccess(forgetPasswordBean.status.message)
//                } else {
//                    view.showInfo(forgetPasswordBean.status.message)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
//    }


}