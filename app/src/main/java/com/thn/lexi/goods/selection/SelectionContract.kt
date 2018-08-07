package com.thn.lexi.goods.selection

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.goods.explore.EditorRecommendBean
import com.thn.lexi.goods.explore.ExploreBannerBean

class SelectionContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {

        }

        fun addData(products: List<GoodsData.DataBean.ProductsBean>) {

        }

        fun loadMoreEnd(){}
        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun setBannerData(banner_images: List<ExploreBannerBean.DataBean.BannerImagesBean>){}
        fun setTodayRecommendData(products: List<EditorRecommendBean.DataBean.ProductsBean>){}
        fun setHotRecommendData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {}
        fun setHotRecommendBannerData(banner_images: List<SelectionHotRecommendBannerBean.DataBean.BannerImagesBean>) {

        }

        fun setDiscoverLifeData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {

        }

        fun setGoodSelectionData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {

        }

        fun setZCManifestData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(cid: String, page: Int)

        fun loadMoreData(cid: String, page: Int)
    }
}