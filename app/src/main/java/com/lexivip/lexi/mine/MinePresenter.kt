package com.lexivip.lexi.mine
import com.lexivip.lexi.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import java.io.IOException

class MinePresenter(view: MineContract.View) : MineContract.Presenter {

    private var view:MineContract.View = checkNotNull(view)

    private val dataSource: MineModel by lazy { MineModel() }

    override fun loadData() {
        dataSource.loadData(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
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

}