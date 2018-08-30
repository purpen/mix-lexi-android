package com.thn.lexi.index.detail

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.ProductBean

class GoodsDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setData(data: GoodsAllDetailBean.DataBean) {

        }

        fun setGoodsInfo(data: ProductBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(goodsId:String)
    }
}