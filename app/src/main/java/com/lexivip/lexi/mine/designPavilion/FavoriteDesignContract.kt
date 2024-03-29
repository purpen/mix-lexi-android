package com.lexivip.lexi.mine.designPavilion

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

        fun setBrandPavilionFocusState(favorite: Boolean, position: Int) {

        }


    }

    interface Presenter : BasePresenter {
        fun loadData(b: Boolean)
        fun loadData(b: Boolean,uid:String)
        fun loadMoreData()
        fun loadMoreData(uid: String)
        fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, position: Int, v: android.view.View)
    }
}