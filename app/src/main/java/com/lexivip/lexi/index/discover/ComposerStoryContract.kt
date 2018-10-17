package com.lexivip.lexi.index.discover

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.LifeWillBean


class ComposerStoryContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setNewData(life_records: List<LifeWillBean>) {

        }
        fun addData(life_records: List<LifeWillBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun loadMoreFail() {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh: Boolean, imageId: Int)
    }
}