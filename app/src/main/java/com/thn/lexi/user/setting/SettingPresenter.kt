package com.thn.lexi.user.setting

import com.basemodule.tools.JsonUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.R
import java.io.IOException

class SettingPresenter(view: SettingContract.View) : SettingContract.Presenter {
    private var view: SettingContract.View = checkNotNull(view)

    private val dataSource: SettingModel by lazy { SettingModel() }

    override fun loadData(userId: String) {
        dataSource.loadData(userId, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val userInfoBean = JsonUtil.fromJson(json, UserInfoBean::class.java)
                if (userInfoBean.success) {
                    view.setUserInfo(userInfoBean.data)
                } else {
                    view.showError(userInfoBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                ToastUtil.showError(R.string.text_net_error)
            }
        })
    }
}