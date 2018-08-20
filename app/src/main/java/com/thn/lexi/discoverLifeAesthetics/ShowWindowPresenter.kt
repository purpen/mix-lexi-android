package com.thn.lexi.discoverLifeAesthetics
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class ShowWindowPresenter(view: ShowWindowContract.View) : ShowWindowContract.Presenter {
    private var view: ShowWindowContract.View = checkNotNull(view)

    private val dataSource: ShowWindowModel by lazy { ShowWindowModel() }

    private var page: Int = 1
    /**
     * 加载数据
     */
    override fun loadData(isRefresh: Boolean) {
        if (isRefresh) this.page = 1

        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowBean::class.java)
                if (showWindowBean.success) {
                    view.setNewData(showWindowBean.data.shop_windows)
                    ++page
                } else {
                    view.showError(showWindowBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载更多
     */
    override fun loadMoreData() {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val showWindowBean = JsonUtil.fromJson(json, ShowWindowBean::class.java)
                if (showWindowBean.success) {
                    val shopWindows = showWindowBean.data.shop_windows
                    if (shopWindows.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(shopWindows)
                        ++page
                    }
                } else {
                    view.showError(showWindowBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}