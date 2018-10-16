package com.thn.lexi.index.discover

import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class ComposerStoryPresenter(view: ComposerStoryContract.View) : ComposerStoryContract.Presenter {

    private var view: ComposerStoryContract.View = checkNotNull(view)

    private val dataSource: ComposerStoryModel by lazy { ComposerStoryModel() }

    private var page = 1

    private var imageId:Int = -1

    override fun loadData(isRefresh: Boolean, imageId: Int) {
        this.imageId = imageId
        if (isRefresh) page = 1
        dataSource.loadData(page, imageId,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val storyListBean = JsonUtil.fromJson(json, StoryListBean::class.java)
                if (storyListBean.success) {
                    view.setNewData(storyListBean.data.life_records)
                    page++
                } else {
                    view.showError(storyListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    fun loadMoreData() {
        dataSource.loadData(page,imageId,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val storyListBean = JsonUtil.fromJson(json, StoryListBean::class.java)
                if (storyListBean.success) {
                    val stories = storyListBean.data.life_records
                    if (stories.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(stories)
                        page++
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(storyListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}