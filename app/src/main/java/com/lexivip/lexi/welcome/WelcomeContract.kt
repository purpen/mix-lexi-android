package com.lexivip.lexi.welcome

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.mine.UserCenterBean
import com.lexivip.lexi.user.LoginWXBean

class WelcomeContract{
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun showError(s: String)
        fun dismissLoadingView()
        fun setBind()
        fun setFinish()
    }
    interface Presenter : BasePresenter {
        fun bindWX(map:Map<String,String>)
    }
}