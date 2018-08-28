package com.thn.lexi.mine

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.selection.GoodsData

class FavoriteContract {

    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {

        }

    }

    interface Presenter : BasePresenter {
        fun loadData(cid: String, page: Int)
    }
}