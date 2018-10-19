package com.lexivip.lexi.selectionGoodsCenter

import com.basemodule.tools.ToastUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import java.io.IOException

class PutAwayActivityPresenter(view: PutAwayActivityContract.View) : PutAwayActivityContract.Presenter {
    private var view: PutAwayActivityContract.View = checkNotNull(view)
    private val dataSource: PutAwayActivityModel by lazy { PutAwayActivityModel() }



    override fun putAwayGoods(rid: String, content: String) {
        dataSource.putAwayGoods(rid,content, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val putAwayBean = JsonUtil.fromJson(json, PutAwayBean::class.java)
                if (putAwayBean.success) {
                    view.goPage()
                    ToastUtil.showSuccess("商品已上架")
                } else {
                    view.showError(putAwayBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


}