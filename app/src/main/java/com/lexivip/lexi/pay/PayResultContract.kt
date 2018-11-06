package com.lexivip.lexi.pay

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class PayResultContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(error: String)
        fun setPayResultData(data: PayResultBean.DataBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(rid:String)
    }
}