package com.lexivip.lexi.index.selection.applyForLifeHouse

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.user.login.ApplyForLifeHouseBean

class ApplyForLifeHouseContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun showInfo(message: String)


        fun startCountDown() {

        }

        fun applySuccess(applyForLifeHouseBean: ApplyForLifeHouseBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun applyForLifeHouse(name: String, job: String,countryCode:String,phone:String,checkCode:String)
        fun sendCheckCode(areaCode: String, phone: String)
    }
}