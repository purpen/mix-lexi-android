package com.thn.lexi.mine

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.selection.GoodsData

class MineContract {



    interface View : BaseView<Presenter> {

        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setUserData(data: UserCenterBean.DataBean) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData()
    }
}