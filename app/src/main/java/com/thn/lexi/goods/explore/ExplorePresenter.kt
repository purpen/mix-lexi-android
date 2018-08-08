package com.thn.lexi.goods.explore
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.Constants
import com.thn.lexi.R
import com.thn.lexi.goods.selection.GoodsData
import java.io.IOException

class ExplorePresenter(view: ExploreContract.View) : ExploreContract.Presenter {


    private var view: ExploreContract.View = checkNotNull(view)

    private val dataSource: ExploreModel by lazy { ExploreModel() }

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

    /**
     * 获取商品分类
     */
    fun getGoodsClass() {
        dataSource.getGoodsClass( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val goodsClassBean = JsonUtil.fromJson(json, GoodsClassBean::class.java)
                if (goodsClassBean.status.code==Constants.SUCCESS) {
                    view.setGoodsClassData(goodsClassBean.data.categories)
                } else {
                    view.showError(goodsClassBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取Banner
     */
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
     * 获取编辑推荐
     */
    fun getEditorRecommend() {
        dataSource.getEditorRecommend( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setEditorRecommendData(editorRecommendBean.data.products)
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
     * 获取品牌馆
     */
    fun getBrandPavilion() {
        dataSource.getBrandPavilion( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val brandPavilionBean = JsonUtil.fromJson(json, BrandPavilionBean::class.java)
                if (brandPavilionBean.success) {
                    view.setBrandPavilionData(brandPavilionBean.data.stores)
                } else {
                    view.showError(brandPavilionBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取优质新品
     * bean共用
     */
    fun getFeatureNewGoods() {
        dataSource.getFeatureNewGoods( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setFeatureNewGoodsData(editorRecommendBean.data.products)
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
     * 品牌馆取消关注
     */
    fun unFocusBrandPavilion(rid:String) {
        dataSource.unFocusBrandPavilion(rid,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setFeatureNewGoodsData(editorRecommendBean.data.products)
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
     * 关注品牌馆
     */
    fun focusBrandPavilion(rid:String) {
        dataSource.focusBrandPavilion(rid,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setFeatureNewGoodsData(editorRecommendBean.data.products)
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
     * 获取商品集合
     */
    fun getGoodsCollection() {
        dataSource.getGoodsCollection(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val goodsCollectionBean = JsonUtil.fromJson(json, GoodsCollectionBean::class.java)
                if (goodsCollectionBean.success) {
                    view.setGoodsCollectionData(goodsCollectionBean.data.collections)
                } else {
                    view.showError(goodsCollectionBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}