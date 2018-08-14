package com.thn.lexi.goods.selection
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.goods.explore.EditorRecommendBean
import com.thn.lexi.goods.explore.ExploreBannerBean
import java.io.IOException

class SelectionPresenter(view: SelectionContract.View) : SelectionContract.Presenter {


    private var view: SelectionContract.View = checkNotNull(view)

    private val dataSource: SelectionModel by lazy { SelectionModel() }

    override fun loadData(cid: String, page: Int) {
        dataSource.loadData(cid, page,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val goodsData = JsonUtil.fromJson(json, GoodsData::class.java)
                if (goodsData.success) {
                    view.setNewData(goodsData.data.products)
                } else {
                    view.showError(goodsData.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    override fun loadMoreData(cid: String, page: Int) {
        dataSource.loadData(cid, page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val goodsData = JsonUtil.fromJson(json, GoodsData::class.java)
                if (goodsData.success) {
                    val products = goodsData.data.products
                    if (products.isEmpty() ){
                        view.loadMoreEnd()
                    }else{
                        view.loadMoreComplete()
                    }
                    view.addData(products)
                } else {
                    view.showError(goodsData.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }





    fun getBanners() {
        dataSource.getBanners( object : IDataSource.HttpRequestCallBack {
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
    fun getTodayRecommend() {
        dataSource.getTodayRecommend( object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }
            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setTodayRecommendData(editorRecommendBean.data.products)
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
     * 获取人气推荐
     */
    fun getHotRecommend() {
        dataSource.getHotRecommend( object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
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
    fun getHotRecommendBanner() {
        dataSource.getHotRecommendBanner( object : IDataSource.HttpRequestCallBack {
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
    fun getDiscoverLife() {
        dataSource.getDiscoverLife( object : IDataSource.HttpRequestCallBack {
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
    fun getGoodSelection() {
        dataSource.getGoodSelection( object : IDataSource.HttpRequestCallBack {
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
    fun getZCManifest() {
        dataSource.getZCManifest( object : IDataSource.HttpRequestCallBack {
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
    fun getHeadLine() {
        dataSource.getHeadLine( object : IDataSource.HttpRequestCallBack {
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