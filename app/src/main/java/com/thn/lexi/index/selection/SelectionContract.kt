package com.thn.lexi.index.selection

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.bean.BannerImageBean
import com.thn.lexi.index.explore.ExploreBannerBean

class SelectionContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setFavorite(b: Boolean, position: Int) {

        }

        fun setBannerData(banner_images: List<BannerImageBean>){}
        fun setTodayRecommendData(products: List<ProductBean>){}
        fun setHotRecommendData(products: List<ProductBean>) {}
        fun setHotRecommendBannerData(banner_images: List<BannerImageBean>) {

        }

        fun setDiscoverLifeData(products: List<DiscoverLifeBean.DataBean.ShopWindowsBean>) {

        }

        fun setGoodSelectionData(products: List<ProductBean>) {

        }

        fun setZCManifestData(products: List<ZCManifestBean.DataBean.LifeRecordsBean>) {

        }

        fun setHeadLineData(data: MutableList<HeadLineBean.DataBean.HeadlinesBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun getBanners()

        fun getTodayRecommend()

        fun getHotRecommend()

        fun getHotRecommendBanner()

        fun getDiscoverLife()

        fun getGoodSelection()

        fun getZCManifest()

        fun getHeadLine()
    }
}