package com.lexivip.lexi.coupon

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.CouponBean

class UserCouponContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun loadMoreComplete()

        fun loadMoreEnd()

        fun loadMoreFail()

        fun setNewData(coupons: List<CouponBean>) {
        }

        fun addData(coupons: MutableList<CouponBean>) {

        }

    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh:Boolean,whichPage:String)
        fun loadMoreData()
    }
}