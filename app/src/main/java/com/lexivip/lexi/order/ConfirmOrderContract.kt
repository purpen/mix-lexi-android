package com.lexivip.lexi.order

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import org.json.JSONObject
import com.lexivip.lexi.beans.CouponBean

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

        fun setDefaultExpressCompany(data: JSONObject) {

        }

        fun setPerOrderFullReductionData(data: JSONObject) {

        }

        fun setCalculateExpressExpenseForEachOrder(data: JSONObject) {

        }

        fun setPavilionCouponByOrderData(data: JSONObject) {

        }

        fun setSubmitOrderSuccess(string: String) {

        }

        fun setOfficialCouponData(coupons: List<CouponBean>) {

        }

    }

    interface Presenter : BasePresenter {
        fun loadData()

        fun getNewUserFirstOrderDiscounts()

        fun getPerOrderFullReduction(list: ArrayList<FullReductionRequestBean>)

        fun getDefaultExpressCompany(stores: ArrayList<FullReductionRequestBean>)
//        fun getOfficialCoupons(price: Double, param: IDataSource.HttpRequestCallBack)
        fun getOfficialCoupons(price: Double, list: ArrayList<String>)
        fun submitOrder(createOrderBean: CreateOrderBean)
    }
}