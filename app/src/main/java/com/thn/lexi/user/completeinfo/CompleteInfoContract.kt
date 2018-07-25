package com.thn.lexi.user.completeinfo

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class CompleteInfoContract {
    interface View : BaseView<Presenter>{
        fun showLoadingView()
        fun showError(s: String)
        fun goPage()
        fun dismissLoadingView()
        fun showInfo(string: String)
    }
    interface Presenter : BasePresenter {
        /**
         * 上传头像
         */
        fun uploadAvatar()

        /**
         * 上传用户信息
         */
        fun uploadUserInfo(avatar_id: String, name: String, birth: String, gender: String)
    }
}