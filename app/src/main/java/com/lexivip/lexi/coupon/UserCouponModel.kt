package com.lexivip.lexi.coupon

import com.basemodule.tools.Constants
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class UserCouponModel {

    companion object {
        const val STATUS_VALID = "N01" //有效券
        const val STATUS_INVALID = "N03" //无效券
    }

    fun loadData(page: Int, whichPage: String, callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDefaultParams()
        params["page"] = "$page"
        params["per_page"] = Constants.PAGE_SIZE
        var method: String = HttpRequest.GET
        var url = ""
        when (whichPage) {
            FragmentUserCoupon.PAGE_LX -> { //官方券
                method = HttpRequest.GET
                url = "${URL.BASE_URL}market/user_official"
            }

            FragmentUserCoupon.PAGE_INVALID -> {//失效券
                url = "${URL.BASE_URL}market/user_expired"
            }

            FragmentUserCoupon.PAGE_BRAND_PAVILION -> {//品牌馆券
                method = HttpRequest.POST
                params["status"] = STATUS_VALID
                url = "${URL.BASE_URL}market/core_user_coupons"
            }
        }

        HttpRequest.sendRequest(method, url, params, object : IDataSource.HttpRequestCallBack {
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

}


