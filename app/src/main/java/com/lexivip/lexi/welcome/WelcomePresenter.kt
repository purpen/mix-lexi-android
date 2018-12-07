package com.lexivip.lexi.welcome

import com.basemodule.tools.Constants
import com.basemodule.tools.LogUtil
import com.basemodule.tools.SPUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.user.LoginWXBean
import com.lexivip.lexi.user.login.UserProfileBean
import com.umeng.message.PushAgent
import com.umeng.message.UTrack
import java.io.IOException

class WelcomePresenter(view: WelcomeContract.View) :WelcomeContract.Presenter{
    private var view: WelcomeContract.View = checkNotNull(view)
    private val dataSource:WelcomeModel by lazy{WelcomeModel()}
    override fun bindWX(map: Map<String, String>) {
        dataSource.bindWX(map,object :IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }
            override fun onSuccess(json: String) {
                LogUtil.e("绑定微信："+json)

                val loginWXBean= JsonUtil.fromJson(json, LoginWXBean::class.java)
                if (loginWXBean.success) {
                    if (loginWXBean.data.is_bind){
                        PushAgent.getInstance(AppApplication.getContext()).addAlias(loginWXBean.data.uid,"lexi",object : UTrack.ICallBack{
                            override fun onMessage(p0: Boolean, p1: String?) {

                            }

                        })
                        SPUtil.write(Constants.AUTHORIZATION, ClientParamsAPI.getAuthorization(loginWXBean.data.token))
                        getUserProfile()
                    }else {
                        view.dismissLoadingView()
                        view.setBind()
                    }
                } else {
                    view.showError(loginWXBean.status.message)
                }
            }
            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                ToastUtil.showError(R.string.text_net_error)
            }
        })
    }

    /**
     * 获取用户信息
     */
    fun getUserProfile() {
        dataSource.getUserProfile(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val userProfileBean = JsonUtil.fromJson(json, UserProfileBean::class.java)
                view.dismissLoadingView()
                if (userProfileBean.success) {
                    SPUtil.write(Constants.USER_PROFILE,json)
                    view.setFinish()
                } else {
                    view.showError(userProfileBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}