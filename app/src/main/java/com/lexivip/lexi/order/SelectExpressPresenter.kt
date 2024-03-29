package com.lexivip.lexi.order
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import java.io.IOException

class SelectExpressPresenter(view: SelectExpressContract.View) : SelectExpressContract.Presenter {

    private var view: SelectExpressContract.View = checkNotNull(view)

    private val dataSource: SelectExpressModel by lazy { SelectExpressModel() }

    /**
     * 加载数据
     */
    override fun loadData(selectExpressRequestBean: SelectExpressRequestBean) {

        dataSource.loadData(selectExpressRequestBean,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
               view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()

                val selectExpressResponseBean = JsonUtil.fromJson(json, SelectExpressResponseBean::class.java)
                if (selectExpressResponseBean.success) {
                    view.setNewData(selectExpressResponseBean.data)
                } else {
                    view.showError(selectExpressResponseBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}