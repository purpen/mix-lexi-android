package com.lexivip.lexi.discoverLifeAesthetics

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ShopWindowBean

class ShowWindowContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(shopWindows: MutableList<ShopWindowBean>) {

        }

        fun addData(shopWindows: MutableList<ShopWindowBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreFail(){}

        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun setFocusState(isFollowed: Boolean, position: Int) {

        }

        fun setRelateShopWIndowData(shop_windows: List<ShopWindowBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh:Boolean)
        fun loadMoreData()
        fun favoriteShowWindow(rid: String,isFavorite:Boolean,position: Int, view1: android.view.View)
        fun focusUser(uid: String, v: android.view.View, isFollowed: Boolean, position: Int)
    }
}