package com.thn.lexi.index.explore.collection
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class CollectionDetailPresenter(view: CollectionDetailContract.View) : CollectionDetailContract.Presenter {
    private var view: CollectionDetailContract.View = checkNotNull(view)

    private val dataSource: CollectionDetailModel by lazy { CollectionDetailModel() }

    /**
     * 加载数据
     */
    override fun loadData(id:String,isRefresh: Boolean) {
        dataSource.loadData(id,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val collectionDetailBean = JsonUtil.fromJson(json, CollectionDetailBean::class.java)
                if (collectionDetailBean.success) {
                    view.setNewData(collectionDetailBean.data)
                } else {
                    view.showError(collectionDetailBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}