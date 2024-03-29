package com.lexivip.lexi.user.setting

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.mine.UserCenterBean
import com.lexivip.lexi.user.LoginWXBean

class SettingContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun showError(s: String)
        fun dismissLoadingView()
        fun setUserInfo(data: UserCenterBean.DataBean)
        fun setBind(bean: LoginWXBean)
    }
    interface Presenter : BasePresenter {
        fun loadData()
        fun bindWX(map:Map<String,String>)
    }
}