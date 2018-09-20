package com.thn.lexi.order

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.basemodule.ui.IDataSource
import com.thn.lexi.beans.CouponBean
import org.json.JSONObject

class ConfirmOrderContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(addresses: MutableList<UserAddressListBean.DataBean>) {

        }


        fun setNewUserDiscountData(data: NewUserDiscountBean.DataBean) {

        }

        fun setPerOrderFullReductionData(data: JSONObject) {

        }

        fun setPavilionCouponByOrderData(data: JSONObject) {

        }

        fun setSubmitOrderSuccess() {

        }

        fun setDefaultExpressCompany(data: JSONObject) {

        }

        fun setCalculateExpressExpenseForEachOrder(data: JSONObject) {

        }



    }

    interface Presenter : BasePresenter {
        fun loadData()

        fun getNewUserFirstOrderDiscounts()

        fun getPerOrderFullReduction(list: ArrayList<FullReductionRequestBean>)

        fun getDefaultExpressCompany(stores: ArrayList<FullReductionRequestBean>)

        fun getOfficialCoupons(price: Double, param: IDataSource.HttpRequestCallBack)

        fun submitOrder(createOrderBean: CreateOrderBean)
    }
}