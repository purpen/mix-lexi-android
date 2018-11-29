package com.lexivip.lexi.mine

import android.support.annotation.NonNull
import android.view.View
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class MineContract {



    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setUserData(data: UserCenterBean.DataBean) {

        }

        fun setFocusState(followed_status: Int) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData()
        fun loadData(uid:String)
        fun focusUser(uid: String, v: android.view.View, focusState: Int)
    }
}