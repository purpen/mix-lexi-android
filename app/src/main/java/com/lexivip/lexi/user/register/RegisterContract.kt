package com.lexivip.lexi.user.register

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class RegisterContract {
    interface View : BaseView<Presenter>{
        fun showLoadingView()
        fun showError(s: String)
        fun goPage(registerBean: RegisterBean)
        fun dismissLoadingView()
        fun showInfo(string: String)
        fun startCountDown()
    }
    interface Presenter : BasePresenter{
        fun sendCheckCode(areaCode:String,phone: String)
        fun verifyCheckCode(areaCode:String,phone: String, checkCode: String)
    }
}