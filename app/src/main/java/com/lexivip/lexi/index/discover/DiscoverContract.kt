package com.lexivip.lexi.index.discover

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.LifeWillBean
import com.lexivip.lexi.index.bean.BannerImageBean


class DiscoverContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setBannerData(banner_images: List<BannerImageBean>) {

        }

        fun setGuessLikeData(life_records: List<LifeWillBean>) {

        }

        fun setWonderfulStoryData(life_records: List<LifeWillBean>) {

        }

    }

    interface Presenter : BasePresenter {
        fun getBanner(isRefresh:Boolean)
        fun getLifeWill(isRefresh:Boolean)
        fun getGuessLike(isRefresh:Boolean)
        fun getWonderfulStory(isRefresh:Boolean)
    }
}