package com.lexivip.lexi.selectionGoodsCenter

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class PutAwayActivityContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
    }

    interface Presenter : BasePresenter {
        fun putAwayGoods(rid: String,content: String)
    }
}