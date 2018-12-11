package com.lexivip.lexi.mine.like
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import com.lexivip.lexi.index.selection.DiscoverLifeBean
import java.io.IOException

class FavoritePresenter(view: FavoriteContract.View) : FavoriteContract.Presenter {

    private var view: FavoriteContract.View = checkNotNull(view)

    private val dataSource: FavoriteModel by lazy { FavoriteModel() }

    /**
     * 获取用户喜欢的商品
     */
    override fun getUserGoodsLike(isRefresh:Boolean) {
        dataSource.getUserGoodsLike(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
               if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setGoodsLikeData(editorRecommendBean.data.products)
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
     * 获取喜欢的橱窗
     */
    override fun getShowWindowLike(isRefresh: Boolean) {
        dataSource.getShowWindowLike(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val discoverLifeBean = JsonUtil.fromJson(json, DiscoverLifeBean::class.java)
                if (discoverLifeBean.success) {
                    view.setShowWindowData(discoverLifeBean.data.shop_windows)
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
     * 获取用户喜欢的商品
     */
    override fun getOtherUserGoodsLike(uid:String,isRefresh: Boolean) {
        dataSource.getOtherUserGoodsLike(uid,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
              if (!isRefresh)  view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setGoodsLikeData(editorRecommendBean.data.products)
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
     * 获取其他用户喜欢的橱窗
     */
    override fun getOtherUserShowWindowLike(uid: String,isRefresh: Boolean) {
        dataSource.getOtherUserShowWindowLike(uid,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val discoverLifeBean = JsonUtil.fromJson(json, DiscoverLifeBean::class.java)
                if (discoverLifeBean.success) {
                    view.setShowWindowData(discoverLifeBean.data.shop_windows)
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