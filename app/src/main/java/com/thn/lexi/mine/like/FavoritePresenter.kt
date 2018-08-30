package com.thn.lexi.mine.like
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.index.explore.EditorRecommendBean
import com.thn.lexi.index.selection.DiscoverLifeBean
import java.io.IOException

class FavoritePresenter(view: FavoriteContract.View) : FavoriteContract.Presenter {

    private var view: FavoriteContract.View = checkNotNull(view)

    private val dataSource: FavoriteModel by lazy { FavoriteModel() }

    /**
     * 获取用户喜欢的商品
     */
    override fun getUserGoodsLike() {
        dataSource.getUserGoodsLike(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
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
    override fun getShowWindowLike() {
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

}