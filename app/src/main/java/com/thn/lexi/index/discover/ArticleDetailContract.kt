package com.thn.lexi.index.discover

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView


class ArticleDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setData(data: ArticleDetailBean.DataBean) {

        }

        fun setFocusState(followed_status: Int) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh: Boolean, rid: String)
        fun focusUser(uid: String, v: android.view.View, isFollow: Boolean)
    }
}