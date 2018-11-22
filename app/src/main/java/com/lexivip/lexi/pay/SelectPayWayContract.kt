package com.lexivip.lexi.pay
import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class SelectPayWayContract {
    interface View:BaseView<Presenter>{
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun savePayWaySuccess() {

        }
    }

    interface Presenter:BasePresenter{
        fun savePayWay(rid: String, payType: String)
    }
}