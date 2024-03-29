package com.lexivip.lexi.order

import com.basemodule.tools.ToastUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.detail.ShopCouponListBean
import org.json.JSONObject
import java.io.IOException

class ConfirmOrderPresenter(view: ConfirmOrderContract.View) : ConfirmOrderContract.Presenter {
    private var view: ConfirmOrderContract.View = checkNotNull(view)

    private val dataSource: ConfirmOrderModel by lazy { ConfirmOrderModel() }

    /**
     * 加载数据
     */
    override fun loadData() {
        dataSource.loadData(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()


                val userAddressListBean = JsonUtil.fromJson(json, UserAddressListBean::class.java)
                if (userAddressListBean.success) {
                    view.setNewData(userAddressListBean.data)
                } else {
                    view.showError(userAddressListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     *  获取新用户首单优惠
     */
    override fun getNewUserFirstOrderDiscounts() {
        dataSource.getNewUserFirstOrderDiscounts(object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val newUserDiscountBean = JsonUtil.fromJson(json, NewUserDiscountBean::class.java)
                if (newUserDiscountBean.success) {
                    view.setNewUserDiscountData(newUserDiscountBean.data)
                } else {
                    view.showError(newUserDiscountBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取每个订单(每店)满减信息
     */
    override fun getPerOrderFullReduction(list: ArrayList<FullReductionRequestBean>) {
        dataSource.getPerOrderFullReduction(list, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val response = JSONObject(json)
                val isSuccess = response.getBoolean("success")
                val status = response.getJSONObject("status")
                val data = response.getJSONObject("data")
                if (isSuccess) {
                    view.setPerOrderFullReductionData(data)
                } else {
                    view.showError(status.getString("message"))
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取默认快递公司
     */
    override fun getDefaultExpressCompany(stores: ArrayList<FullReductionRequestBean>) {
        dataSource.getDefaultExpressCompany(stores, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val response = JSONObject(json)
                val isSuccess = response.optBoolean("success")
                val status = response.optJSONObject("status")
                val data = response.optJSONObject("data")
                if (isSuccess) {
                    view.setDefaultExpressCompany(data)
                } else {
                    view.showError(status.getString("message"))
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 根据获取当前店铺订单优惠券列表
     */
    fun getPavilionCouponByOrder(stores: ArrayList<FullReductionRequestBean>) {

        dataSource.getPavilionCouponByOrder(stores, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val response = JSONObject(json)
                val isSuccess = response.getBoolean("success")
                val status = response.getJSONObject("status")
                val data = response.getJSONObject("data")
                if (isSuccess) {
                    view.setPavilionCouponByOrderData(data)
                } else {
                    view.showError(status.getString("message"))
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     *  提交订单
     */
    override fun submitOrder(createOrderBean: CreateOrderBean) {
        dataSource.submitOrder(createOrderBean, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.setOnBackPressUnable()
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val submitOrderBean = JsonUtil.fromJson(json, SubmitOrderBean::class.java)
                if (submitOrderBean.success) {
                    view.setSubmitOrderSuccess(submitOrderBean.data.order_rid)
                } else {
                    view.showError(submitOrderBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 获取订单运费列表
     */
    fun calculateExpressExpenseForEachOrder(requestBean: CalculateExpressExpenseRequestBean) {
        dataSource.calculateExpressExpenseForEachOrder(requestBean, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val response = JSONObject(json)
                val isSuccess = response.getBoolean("success")
                val status = response.getJSONObject("status")
                val data = response.getJSONObject("data")
                if (isSuccess) {
                    view.setCalculateExpressExpenseForEachOrder(data)
                } else {
                    view.showError(status.getString("message"))
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取官方优惠券
     */
//    override fun getOfficialCoupons(price: Double, callback: IDataSource.HttpRequestCallBack) {
//        dataSource.getOfficialCoupons(price,object : IDataSource.HttpRequestCallBack {
//            override fun onStart() {
//                callback.onStart()
//            }
//
//            override fun onSuccess(json: String) {
//                callback.onSuccess(json)
//            }
//
//            override fun onFailure(e: IOException) {
//                callback.onFailure(e)
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
//    }


    /**
     * 获取官方优惠券
     */
    override fun getOfficialCoupons(price: Double, list: ArrayList<String>) {
        dataSource.getOfficialCoupons(price,list,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val shopCouponListBean = JsonUtil.fromJson(json, ShopCouponListBean::class.java)
                if (shopCouponListBean.success) {
                    view.setOfficialCouponData(shopCouponListBean.data.coupons)
                } else {
                    ToastUtil.showError(shopCouponListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}