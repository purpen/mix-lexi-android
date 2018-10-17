package com.lexivip.lexi.index.detail

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.UserBean

class FavoriteUserListContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(users: MutableList<UserBean>) {

        }

        fun addData(users: MutableList<UserBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun loadMoreFail() {

        }

        fun setFocusState(followed_status: Int, position: Int) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(goodsId:String,isRefresh:Boolean)
        fun loadMoreData(goodsId:String)
        fun focusUser(uid: String, v: android.view.View, focusState: Int, position: Int)
    }
}