package com.lexivip.lexi.user.login

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class LoginContract {
    interface View : BaseView<LoginContract.Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun showInfo(message: String)
        fun goPage(vararg args: Boolean)
        fun showHint() {

        }

        fun startCountDown() {

        }
        fun setBind()
    }

    interface Presenter : BasePresenter {
        fun loginUser(toString: String, toString1: String)
        fun wechatLogin(map: Map<String, String>)
        fun qqLogin()
        fun sinaLogin()
        fun sendCheckCode(areaCode: String, phone: String)
    }
}