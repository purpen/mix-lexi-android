package com.thn.lexi.index.detail

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class SimilarGoodsContract {
    interface View:BaseView<Presenter>{
        fun showError(@NonNull string: String)
        fun goPage()

        fun loadMoreEnd(){}
        fun loadMoreComplete() {

        }

        fun setNewData(products: List<SimilarGoodsBean.DataBean.ProductsBean>) {

        }

        fun addData(products: List<SimilarGoodsBean.DataBean.ProductsBean>) {

        }
    }

    interface Presenter:BasePresenter{

    }
}