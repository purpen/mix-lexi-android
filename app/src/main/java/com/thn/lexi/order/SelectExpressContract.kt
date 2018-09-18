package com.thn.lexi.order

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.UserBean

class SelectExpressContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(users: MutableList<UserBean>) {

        }

        fun addData(users: MutableList<UserBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(expressModelId:String)
    }
}