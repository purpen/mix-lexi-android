package com.thn.lexi.user.login

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class LoginContract {
    interface View : BaseView<LoginContract.Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun showInfo(message: String)
        fun goPage()
    }

    interface Presenter : BasePresenter {
        fun loginUser(toString: String, toString1: String)
        fun wechatLogin()
        fun qqLogin()
        fun sinaLogin()
        fun sendCheckCode(areaCode: String, phone: String)
    }
}