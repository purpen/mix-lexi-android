package com.lexivip.lexi.index.selection

import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.dialog.CouponBean
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import com.lexivip.lexi.index.explore.ExploreBannerBean
import java.io.IOException

class SelectionPresenter(view: SelectionContract.View) : SelectionContract.Presenter {

    private var view: SelectionContract.View = checkNotNull(view)

    private val dataSource: SelectionModel by lazy { SelectionModel() }

    override fun getReceive() {
        dataSource.getReceive(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val couponBean = JsonUtil.fromJson(json, CouponBean::class.java)
                if (couponBean.success) {
                    view.setIsReceive(couponBean.data.is_grant)
                } else {
                    view.showError(couponBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }

        })
    }

    override fun getBanners() {
        dataSource.getBanners(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val exploreBannerBean = JsonUtil.fromJson(json, ExploreBannerBean::class.java)
                if (exploreBannerBean.success) {
                    view.setBannerData(exploreBannerBean.data.banner_images)
                } else {
                    view.showError(exploreBannerBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获得今日推荐
     */
    override fun getTodayRecommend(refresh: Boolean) {
        dataSource.getTodayRecommend(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!refresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val todayRecommendBean = JsonUtil.fromJson(json, TodayRecommendBean::class.java)
                if (todayRecommendBean.success) {
                    view.setTodayRecommendData(todayRecommendBean.data.daily_recommends)
                } else {
                    view.showError(todayRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取人气推荐
     */
    override fun getHotRecommend(refresh: Boolean) {
        dataSource.getHotRecommend(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!refresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setHotRecommendData(editorRecommendBean.data.products)
                } else {
                    view.showError(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 人气推荐banner
     */
    override fun getHotRecommendBanner() {
        dataSource.getHotRecommendBanner(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val selectionHotRecommendBannerBean = JsonUtil.fromJson(json, SelectionHotRecommendBannerBean::class.java)
                if (selectionHotRecommendBannerBean.success) {
                    view.setHotRecommendBannerData(selectionHotRecommendBannerBean.data.banner_images)
                } else {
                    view.showError(selectionHotRecommendBannerBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 发现生活美学
     */
    override fun getDiscoverLife() {
        dataSource.getDiscoverLife(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val discoverLifeBean = JsonUtil.fromJson(json, DiscoverLifeBean::class.java)
                if (discoverLifeBean.success) {
                    view.setDiscoverLifeData(discoverLifeBean.data.shop_windows)
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
     * 优选
     */
    override fun getGoodSelection() {
        dataSource.getGoodSelection(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setGoodSelectionData(editorRecommendBean.data.products)
                } else {
                    view.showError(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 种草清单
     */
    override fun getZCManifest() {
        dataSource.getZCManifest(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val zcManifestBean = JsonUtil.fromJson(json, ZCManifestBean::class.java)
                if (zcManifestBean.success) {
                    view.setZCManifestData(zcManifestBean.data.life_records)
                } else {
                    view.showError(zcManifestBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取头条
     */
    override fun getHeadLine() {
        dataSource.getHeadLine(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val headLineBean = JsonUtil.fromJson(json, HeadLineBean::class.java)
                if (headLineBean.success) {
                    view.setHeadLineData(headLineBean.data.headlines)
                } else {
                    view.showError(headLineBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}