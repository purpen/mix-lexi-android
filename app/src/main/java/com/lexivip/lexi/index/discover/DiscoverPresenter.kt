package com.lexivip.lexi.index.discover

import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.explore.ExploreBannerBean
import java.io.IOException

class DiscoverPresenter(view: DiscoverContract.View):DiscoverContract.Presenter {

    private var view:DiscoverContract.View = checkNotNull(view)

    private val dataSource: DiscoverModel by lazy { DiscoverModel() }

    override fun getBanner(){
        dataSource.getBanner(object: IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val exploreBannerBean = JsonUtil.fromJson(json, ExploreBannerBean::class.java)
                if (exploreBannerBean.success) {
                    if (exploreBannerBean.data!=null) view.setBannerData(exploreBannerBean.data.banner_images)
                } else {
                    view.showError(exploreBannerBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取生活志
     */
    override fun getLifeWill() {
        dataSource.getLifeWill(object: IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
//                val exploreBannerBean = JsonUtil.fromJson(json, ExploreBannerBean::class.java)
//                if (exploreBannerBean.success) {
//                    if (exploreBannerBean.data!=null) view.setBannerData(exploreBannerBean.data.banner_images)
//                } else {
//                    view.showError(exploreBannerBean.status.message)
//                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 猜你喜欢
     */
    override fun getGuessLike() {
        dataSource.getGuessLike(object: IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val storyListBean = JsonUtil.fromJson(json, StoryListBean::class.java)
                if (storyListBean.success) {
                    if (storyListBean.data!=null) view.setGuessLikeData(storyListBean.data.life_records)
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

    /**
     * 获取精彩故事
     */
    override fun getWonderfulStory() {
        dataSource.getWonderfulStory(object: IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val storyListBean = JsonUtil.fromJson(json, StoryListBean::class.java)
                if (storyListBean.success) {
                    if (storyListBean.data!=null) view.setWonderfulStoryData(storyListBean.data.life_records)
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
}