package com.lexivip.lexi.user.setting

import com.lexivip.lexi.JsonUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.R
import com.lexivip.lexi.mine.UserCenterBean
import java.io.IOException

class SettingPresenter(view: SettingContract.View) : SettingContract.Presenter {

    private var view: SettingContract.View = checkNotNull(view)

    private val dataSource: SettingModel by lazy { SettingModel() }

    override fun loadData() {
        dataSource.loadData(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val userCenterBean = JsonUtil.fromJson(json, UserCenterBean::class.java)
                if (userCenterBean.success) {
                    view.setUserInfo(userCenterBean.data)
                } else {
                    view.showError(userCenterBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                ToastUtil.showError(R.string.text_net_error)
            }
        })
    }
    override fun bindWX(openid: String) {
        dataSource.bindWX(openid,object :IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }
            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val userCenterBean = JsonUtil.fromJson(json, UserCenterBean::class.java)
                if (userCenterBean.success) {
                    view.setBind(userCenterBean.success)
                } else {
                    view.showError(userCenterBean.status.message)
                }
            }
            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                ToastUtil.showError(R.string.text_net_error)
            }
        })
    }
}