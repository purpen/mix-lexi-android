package com.thn.lexi.message

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView


class MessageContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()

    }

    interface Presenter : BasePresenter {
        fun loadData(page:Int,userId:String)
    }
}