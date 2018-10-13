package com.thn.lexi.selectionGoodsCenter

import com.basemodule.tools.ToastUtil
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class PutAwayActivityPresenter(view: PutAwayActivityContract.View) : PutAwayActivityContract.Presenter {
    private var view: PutAwayActivityContract.View = checkNotNull(view)
    private val dataSource: PutAwayActivityModel by lazy { PutAwayActivityModel() }



    override fun putAwayGoods(rid: String, store_rid: String, content: String) {
        dataSource.putAwayGoods(rid,store_rid,content, object : IDataSource.HttpRequestCallBack {
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