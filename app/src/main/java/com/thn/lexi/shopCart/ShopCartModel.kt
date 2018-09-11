package com.thn.lexi.shopCart
import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

class ShopCartModel : IDataSource {

    fun loadData(page:Int,httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params =  ClientParamsAPI.getWishOrderParams(page)
        HttpRequest.sendRequest(HttpRequest.GET, URL.ADD_WISH_ORDER, params, object : IDataSource.HttpRequestCallBack {
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


    fun addShopCart(rid: String, quantity: Int, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.addShopCartParams(rid,quantity)
        HttpRequest.sendRequest(HttpRequest.POST, URL.ADD_SHOP_CART, params, object : IDataSource.HttpRequestCallBack {
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



    fun getShopCartGoods(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET, URL.SHOP_CART_GOODS, params, object : IDataSource.HttpRequestCallBack {
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