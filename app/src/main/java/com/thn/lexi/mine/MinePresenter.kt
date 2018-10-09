package com.thn.lexi.mine
import com.thn.lexi.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
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
                LogUtil.e(json)
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