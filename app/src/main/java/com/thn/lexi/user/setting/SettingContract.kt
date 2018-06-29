package com.thn.lexi.user.setting

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class SettingContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun showError(s: String)
        fun dismissLoadingView()
        fun setUserInfo(data: UserInfoBean.DataBean)
    }
    interface Presenter : BasePresenter {
        fun loadData(userId: String)
    }
}