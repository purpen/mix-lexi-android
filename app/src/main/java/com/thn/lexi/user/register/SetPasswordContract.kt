package com.thn.lexi.user.register

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class SetPasswordContract {
    interface View : BaseView<Presenter>{
        fun showLoadingView()
        fun showError(s: String)
        fun goPage()
        fun dismissLoadingView()
        fun showInfo(string: String)
    }
    interface Presenter : BasePresenter {
        fun registerUser(phone: String, password: String,checkCode:String)
    }
}