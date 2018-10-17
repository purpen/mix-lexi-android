package com.lexivip.lexi.user.password

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class ForgetPasswordContract {
    interface View : BaseView<Presenter>{
        fun showLoadingView()
        fun showError(s: String)
        fun goPage(email: String)
        fun dismissLoadingView()
        fun showInfo(string: String)
    }
    interface Presenter : BasePresenter {

    }
}