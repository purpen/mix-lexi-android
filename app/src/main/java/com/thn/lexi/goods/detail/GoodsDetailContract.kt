package com.thn.lexi.goods.detail

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class GoodsDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setData(data: GoodsDetailBean.DataBean) {

        }

        fun setGoodsInfo(data: GoodsInfoBean.DataBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(goodsId:String)
    }
}