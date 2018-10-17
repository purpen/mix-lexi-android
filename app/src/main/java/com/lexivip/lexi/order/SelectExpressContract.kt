package com.lexivip.lexi.order

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class SelectExpressContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(users: MutableList<ExpressInfoBean>) {

        }

    }

    interface Presenter : BasePresenter {
        fun loadData(selectExpressRequestBean: SelectExpressRequestBean)
    }
}