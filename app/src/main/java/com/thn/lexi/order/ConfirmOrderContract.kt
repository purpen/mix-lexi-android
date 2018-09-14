package com.thn.lexi.order

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import org.json.JSONObject

class ConfirmOrderContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(addresses: MutableList<UserAddressListBean.DataBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun loadMoreFail() {

        }

        fun setNewUserDiscountData(data: NewUserDiscountBean.DataBean) {

        }

        fun setPerOrderFullReductionData(data: JSONObject) {

        }

        fun setPavilionCouponByOrderData(data: JSONObject) {

        }

    }

    interface Presenter : BasePresenter {
        fun loadData()

        fun getNewUserFirstOrderDiscounts()

        fun getPerOrderFullReduction(list: ArrayList<FullReductionRequestBean>)
    }
}