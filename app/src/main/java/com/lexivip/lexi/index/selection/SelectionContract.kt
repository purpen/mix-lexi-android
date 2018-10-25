package com.lexivip.lexi.index.selection

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.LifeWillBean
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.index.bean.BannerImageBean

class SelectionContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setFavorite(b: Boolean, position: Int) {

        }

        fun setBannerData(banner_images: List<BannerImageBean>){}
        fun setTodayRecommendData(products: MutableList<TodayRecommendBean.DataBean.DailyRecommendsBean>){}
        fun setHotRecommendData(products: List<ProductBean>) {}
        fun setHotRecommendBannerData(banner_images: List<BannerImageBean>) {

        }

        fun setDiscoverLifeData(products: List<ShopWindowBean>) {

        }

        fun setGoodSelectionData(products: List<ProductBean>) {

        }

        fun setZCManifestData(products: List<LifeWillBean>) {

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