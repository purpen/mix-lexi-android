package com.thn.lexi.selectionGoodsCenter

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.bean.BannerImageBean
import com.thn.lexi.index.explore.ExploreBannerBean
import com.thn.lexi.index.selection.HeadLineBean

class GoodsRecommendContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()


        fun setBannerData(banner_images: List<BannerImageBean>){}


        fun setHeadLineData(data: MutableList<HeadLineBean.DataBean.HeadlinesBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(cid: String, page: Int)
    }
}