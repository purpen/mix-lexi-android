package com.lexivip.lexi.mine.dynamic

import android.support.annotation.NonNull
import android.view.View
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class DynamicContract {

    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()

        fun setNewData(data: DynamicBean.DataBean) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun addData(data: DynamicBean.DataBean) {

        }

        fun loadMoreFail() {

        }

        fun deleteShopWindow(position: Int) {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }


    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh: Boolean,uid:String)
        fun loadMoreData()
        fun favoriteShowWindow(rid: String,isFavorite:Boolean,position: Int, view1: android.view.View)
    }
}