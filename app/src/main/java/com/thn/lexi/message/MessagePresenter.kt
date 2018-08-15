package com.thn.lexi.message

import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class MessagePresenter(view: MessageContract.View):MessageContract.Presenter {

    private var view:MessageContract.View = checkNotNull(view)

    private val dataSource: MessageModel by lazy { MessageModel() }

    override fun loadData(page:Int,userId: String) {
        dataSource.loadData(page,userId,object: IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val messageBean = JsonUtil.fromJson(json, MessageBean::class.java)
//                if (goodsData.success) {
//                    if (goodsData.data!=null) view_selection_goods_center_recommend.setData(goodsData.data)
//                } else {
//                    view_selection_goods_center_recommend.showError(goodsData.status.message)
//                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}