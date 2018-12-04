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
        fun setBindPhoneCode()
    }
    interface Presenter : BasePresenter{
        fun sendCheckCode(areaCode:String,phone: String)
        fun verifyCheckCode(areaCode:String,phone: String, checkCode: String)
        fun bindPhoneCode(openid:String,areaCode: String, phone: String, checkCode: String)
    }
}