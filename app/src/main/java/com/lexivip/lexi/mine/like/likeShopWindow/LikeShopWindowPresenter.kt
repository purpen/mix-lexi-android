package com.lexivip.lexi.mine.like.likeShopWindow
import com.basemodule.tools.LogUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.selection.DiscoverLifeBean
import java.io.IOException

class LikeShopWindowPresenter(view: LikeShopWindowContract.View) : LikeShopWindowContract.Presenter {
    private var view: LikeShopWindowContract.View = checkNotNull(view)

    private val dataSource: LikeShopWindowModel by lazy { LikeShopWindowModel() }

    private var  isRefresh =false

    private var page: Int = 1

    /**
     * 默认参数加载数据
     */
    override fun loadData(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        if (isRefresh) this.page = 1

        dataSource.loadData(page,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                LogUtil.e(json)
                val discoverLifeBean = JsonUtil.fromJson(json, DiscoverLifeBean::class.java)
                if (discoverLifeBean.success) {
                    view.setNewData(discoverLifeBean.data.shop_windows)
                    page++
                } else {
                    view.showError(discoverLifeBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
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
                val discoverLifeBean = JsonUtil.fromJson(json, DiscoverLifeBean::class.java)
                if (discoverLifeBean.success) {
                    val windows = discoverLifeBean.data.shop_windows
                    if (windows.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(windows)
                        ++page
                    }
                } else {
                    view.showError(discoverLifeBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}