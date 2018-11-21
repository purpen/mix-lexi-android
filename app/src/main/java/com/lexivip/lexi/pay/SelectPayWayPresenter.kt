package com.lexivip.lexi.pay
import com.basemodule.tools.Util
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.net.NetStatusBean
import java.io.IOException

class SelectPayWayPresenter(view: SelectPayWayContract.View) : SelectPayWayContract.Presenter {
    private var view: SelectPayWayContract.View = checkNotNull(view)
    private val dataSource: SelectPayWayModel by lazy { SelectPayWayModel() }
    override fun savePayWay(rid: String, payType: String) {
        dataSource.savePayWay(rid,payType,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    view.savePayWaySuccess()
                } else {
                    view.showError(netStatusBean.status.message)
                }

            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(Util.getString(R.string.text_net_error))
            }
        })
    }
}