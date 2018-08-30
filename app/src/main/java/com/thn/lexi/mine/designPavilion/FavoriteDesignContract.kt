package com.thn.lexi.mine.designPavilion

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class FavoriteDesignContract {

    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()

        fun setNewData(data: MutableList<DesignPavilionBean>) {

        }

        fun loadMoreEnd() {
            
        }

        fun loadMoreComplete() {
            
        }

        fun addData(stores: MutableList<DesignPavilionBean>) {

        }


    }

    interface Presenter : BasePresenter {
        fun loadData()
        fun loadMoreData()
    }
}