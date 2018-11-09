package com.lexivip.lexi.mine.like.likeShopWindow
import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ShopWindowBean

class LikeShopWindowContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setNewData(data: MutableList<ShopWindowBean>) {
        }

        fun addData(windows: MutableList<ShopWindowBean>) {

        }

        fun loadMoreEnd() {}
        fun loadMoreComplete() {}
        fun loadMoreFail() {}
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh: Boolean)
        fun loadData(uid: String, isRefresh: Boolean)
        fun loadMoreData()
        fun loadMoreData(uid: String)
    }
}