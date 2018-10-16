package com.thn.lexi.selectionGoodsCenter

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.bean.BannerImageBean

class HotGoodsContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()


        fun setBannerData(banner_images: List<BannerImageBean>){}


        fun setNewData(products: MutableList<ProductBean>) {

        }

        fun addData(products: List<ProductBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData()
    }
}