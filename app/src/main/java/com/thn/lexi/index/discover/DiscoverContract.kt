package com.thn.lexi.index.discover

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.LifeWillBean
import com.thn.lexi.index.bean.BannerImageBean


class DiscoverContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setBannerData(banner_images: List<BannerImageBean>) {

        }

        fun setGuessLikeData(life_records: List<LifeWillBean>) {

        }

        fun setWonderfulStoryData(life_records: List<LifeWillBean>) {

        }

    }

    interface Presenter : BasePresenter {
        fun getBanner()
        fun getLifeWill()
        fun getGuessLike()
        fun getWonderfulStory()
    }
}