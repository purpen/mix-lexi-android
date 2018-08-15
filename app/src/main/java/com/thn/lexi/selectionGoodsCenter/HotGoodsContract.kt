package com.thn.lexi.selectionGoodsCenter

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.explore.ExploreBannerBean
import com.thn.lexi.index.selection.HeadLineBean

class HotGoodsContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()


        fun setBannerData(banner_images: List<ExploreBannerBean.DataBean.BannerImagesBean>){}


        fun setHeadLineData(data: MutableList<HeadLineBean.DataBean.HeadlinesBean>) {

        }

        fun setNewData(products: MutableList<HotGoodsBean.DataBean.ProductsBean>) {

        }

        fun addData(products: List<HotGoodsBean.DataBean.ProductsBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(page: Int)
    }
}