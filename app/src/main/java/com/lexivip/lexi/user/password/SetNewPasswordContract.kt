package com.lexivip.lexi.user.password

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class SetNewPasswordContract {
    interface View : BaseView<Presenter>{
        fun showLoadingView()
        fun showError(s: String)
        fun goPage()
        fun dismissLoadingView()
        fun showInfo(string: String)
    }
    interface Presenter : BasePresenter {
        fun updateNewPassword(phone:String,password: String, confirmPassword: String)
    }
}