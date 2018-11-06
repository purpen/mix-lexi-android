package com.lexivip.lexi.pay

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import java.io.IOException

class PayResultPresenter(view: PayResultContract.View) : PayResultContract.Presenter{
    private var view: PayResultContract.View = checkNotNull(view)

    private val dataSource: PayResultModel by lazy { PayResultModel() }

    override fun loadData(rid:String) {
        dataSource.loadData(rid,object :IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val payResultBean = JsonUtil.fromJson(json, PayResultBean::class.java)
                if (payResultBean.success) {
                    view.setPayResultData(payResultBean.data)
                } else {
                    view.showError(payResultBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}