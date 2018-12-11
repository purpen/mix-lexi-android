package com.lexivip.lexi.mine
import android.view.View
import com.basemodule.tools.LogUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.discoverLifeAesthetics.UserFocusState
import java.io.IOException

class MinePresenter(view: MineContract.View) : MineContract.Presenter {

    private var view:MineContract.View = checkNotNull(view)

    private val dataSource: MineModel by lazy { MineModel() }

    override fun loadData(isRefresh: Boolean) {
        dataSource.loadData(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
              if (!isRefresh)  view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val userCenterBean = JsonUtil.fromJson(json, UserCenterBean::class.java)
                if (userCenterBean.success) {
                    view.setUserData(userCenterBean.data)
                } else {
                    view.showError(userCenterBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    override fun loadData(uid:String,isRefresh:Boolean) {
        dataSource.loadData(uid,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
               if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val userCenterBean = JsonUtil.fromJson(json, UserCenterBean::class.java)
                if (userCenterBean.success) {
                    view.setUserData(userCenterBean.data)
                } else {
                    view.showError(userCenterBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 关注用户
     */
    override fun focusUser(uid: String, v: View, focusState: Int) {
        dataSource.focusUser(uid,focusState, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
            }

            override fun onSuccess(json: String) {
                v.isEnabled = true
                val userFocusState = JsonUtil.fromJson(json, UserFocusState::class.java)
                if (userFocusState.success) {
                    view.setFocusState(userFocusState.data.followed_status)
                } else {
                    view.showError(userFocusState.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                v.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}