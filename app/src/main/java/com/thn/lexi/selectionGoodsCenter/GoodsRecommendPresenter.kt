package com.thn.lexi.selectionGoodsCenter
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.index.explore.ExploreBannerBean
import com.thn.lexi.index.selection.GoodsData
import com.thn.lexi.index.selection.HeadLineBean
import java.io.IOException

class GoodsRecommendPresenter(view: GoodsRecommendContract.View) : GoodsRecommendContract.Presenter {
    private var view: GoodsRecommendContract.View = checkNotNull(view)

    private val dataSource: GoodsRecommendModel by lazy { GoodsRecommendModel() }

    override fun loadData(cid: String, page: Int) {
        dataSource.loadData(cid, page,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val goodsData = JsonUtil.fromJson(json, GoodsData::class.java)
                if (goodsData.success) {
//                    view_selection_goods_center_recommend.setNewData(goodsData.data.products)
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