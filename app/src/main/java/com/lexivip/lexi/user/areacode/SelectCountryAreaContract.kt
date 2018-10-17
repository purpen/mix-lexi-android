package com.lexivip.lexi.user.areacode

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class SelectCountryAreaContract {
    interface View : BaseView<SelectCountryAreaContract.Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setNewData(area_codes: List<CountryAreaCodeBean.DataBean.AreaCodesBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {
            
        }

        fun addData(area_codes: List<CountryAreaCodeBean.DataBean.AreaCodesBean>) {

        }
    }

    interface Presenter : BasePresenter {

    }
}