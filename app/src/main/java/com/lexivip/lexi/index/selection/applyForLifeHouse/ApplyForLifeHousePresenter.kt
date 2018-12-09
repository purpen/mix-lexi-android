package com.lexivip.lexi.index.selection.applyForLifeHouse
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.user.login.ApplyForLifeHouseBean
import com.lexivip.lexi.user.password.VerifyCodeBean
import java.io.IOException

class ApplyForLifeHousePresenter(view: ApplyForLifeHouseContract.View) : ApplyForLifeHouseContract.Presenter {
    private var view: ApplyForLifeHouseContract.View = checkNotNull(view)
    private val dataSource: ApplyForLifeHouseModel by lazy { ApplyForLifeHouseModel() }

    /**
     * 登录
     */
    override fun applyForLifeHouse(name: String, job: String,countryCode:String,phone:String,checkCode:String) {
        dataSource.applyForLifeHouse(name,job, countryCode,phone,checkCode,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val applyForLifeHouseBean = JsonUtil.fromJson(json, ApplyForLifeHouseBean::class.java)
                if (applyForLifeHouseBean.success) {
                    view.applySuccess(applyForLifeHouseBean)
                    ToastUtil.showSuccess(Util.getString(R.string.text_commit_success))
                } else {
                    view.showError(applyForLifeHouseBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    override fun sendCheckCode(areaCode: String, phone: String) {
        dataSource.sendCheckCode(areaCode, phone, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val verifyCodeBean = JsonUtil.fromJson(json, VerifyCodeBean::class.java)
                if (verifyCodeBean.success) {
                    view.startCountDown()
                    ToastUtil.showSuccess(AppApplication.getContext().getString(R.string.text_check_code_sended))
                } else {
                    view.showInfo(verifyCodeBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}