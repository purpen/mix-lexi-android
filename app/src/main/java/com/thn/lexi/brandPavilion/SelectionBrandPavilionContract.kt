package com.thn.lexi.brandPavilion

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.explore.ExploreBannerBean

class SelectionBrandPavilionContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setSelectionPavilionData(handpick_store: List<SelectionBrandPavilionListBean.DataBean.HandpickStoreBean>) {

        }

        fun setBannerData(banner_images: List<ExploreBannerBean.DataBean.BannerImagesBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun getSelectionPavilion()
        fun getBanners()
    }
}