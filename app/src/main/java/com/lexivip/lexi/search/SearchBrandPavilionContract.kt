package com.lexivip.lexi.search

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.mine.designPavilion.DesignPavilionBean

class SearchBrandPavilionContract {

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

        fun setBrandPavilionFocusState(favorite: Boolean, position: Int) {

        }


    }

    interface Presenter : BasePresenter {
        fun loadData(searchString: String)
        fun loadMoreData()
        fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, position: Int)
    }
}