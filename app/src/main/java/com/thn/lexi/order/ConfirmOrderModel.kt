package com.thn.lexi.order
import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class ConfirmOrderModel{

    fun loadData(callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET, URL.GET_USER_EXPRESS_ADDRESS, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callBack.onStart()
            }

            override fun onSuccess(json: String) {
                callBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callBack.onFailure(e)
            }
        })
    }


    fun getNewUserFirstOrderDiscounts(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET, URL.NEW_USER_FIRST_ORDER_DISCOUNTS, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }

            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
            }
        })
    }

    fun getPerOrderFullReduction(storeList: ArrayList<FullReductionRequestBean>, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getPerOrderFullReductionParams(storeList)
        HttpRequest.sendRequest(HttpRequest.POST, URL.PERORDER_FULL_REDUCTION, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }

            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
            }
        })
    }

    //参数同获取满减
    fun getPavilionCouponByOrder(stores: ArrayList<FullReductionRequestBean>, httpRequestCallBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getPerOrderFullReductionParams(stores)

        HttpRequest.sendRequest(HttpRequest.POST, URL.PAVILION_ORDER_COUPONS, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }

            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
            }
        })
    }


    fun submitOrder(createOrderBean: CreateOrderBean, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getSubmitOrderParams(createOrderBean)
        HttpRequest.sendRequest(HttpRequest.POST, URL.SUBMIT_ORDER, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }

            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
            }
        })
    }

    fun getDefaultExpressCompany(stores: ArrayList<FullReductionRequestBean>, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getPerOrderFullReductionParams(stores)
        HttpRequest.sendRequest(HttpRequest.POST, URL.PRODUCT_EXPRESS, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }
            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }
            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
            }
        })
    }
    fun calculateExpressExpenseForEachOrder(requestBean: CalculateExpressExpenseRequestBean,httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.calculateExpressExpenseForEachOrderParams(requestBean)
        HttpRequest.sendRequest(HttpRequest.POST, URL.FREIGHT_CALCULATE, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }
            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }
            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
            }
        })
    }
    fun getOfficialCoupons(price: Double, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getOfficialCouponsParams(price)
        HttpRequest.sendRequest(HttpRequest.GET, URL.ORDER_OFFICIAL_COUPONS, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }
            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }
            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
            }
        })
    }
}


