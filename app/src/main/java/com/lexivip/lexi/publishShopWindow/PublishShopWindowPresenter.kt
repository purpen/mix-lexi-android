package com.lexivip.lexi.publishShopWindow
import com.basemodule.tools.Util
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import java.io.IOException

class PublishShopWindowPresenter(view: PublishShopWindowContract.View) : PublishShopWindowContract.Presenter {
    private var view: PublishShopWindowContract.View = checkNotNull(view)
    private val dataSource: PublishShopWindowModel by lazy { PublishShopWindowModel() }
    override fun publishShopWindow(title: String, content: String, products: ArrayList<ProductBean>, tagList: ArrayList<String>) {
        dataSource.publishShopWindow(title,content,products,tagList,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val publishShopWindowBean = JsonUtil.fromJson(json, PublishShopWindowBean::class.java)
                if (publishShopWindowBean.success) {
                    view.publishShopWindowSuccess(publishShopWindowBean.data)
                } else {
                    view.showError(publishShopWindowBean.status.message)
                }

            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(Util.getString(R.string.text_net_error))
            }
        })
    }
}