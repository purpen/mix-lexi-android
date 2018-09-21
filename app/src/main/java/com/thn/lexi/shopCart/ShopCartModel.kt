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

    fun getGoodsSKUs(id: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getGoodsSKUsParams(id)
        HttpRequest.sendRequest(HttpRequest.GET, URL.GET_GOODS_SKUS, params, object : IDataSource.HttpRequestCallBack {
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

    fun addWishOrder(list: ArrayList<String>, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getGoodsIdParams(list)

        HttpRequest.sendRequest(HttpRequest.POST, URL.ADD_WISH_ORDER, params, object : IDataSource.HttpRequestCallBack {
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


    fun removeProductFromShopCart(list: ArrayList<String>, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getGoodsIdParams(list)

        HttpRequest.sendRequest(HttpRequest.POST, URL.REMOVE_FROM_SHOP_CART, params, object : IDataSource.HttpRequestCallBack {
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

    fun updateReselectSKU(newSKU: String, oldSKU: String, quantity: Int, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getUpdateReselectSKUParams(newSKU,oldSKU,quantity)
        HttpRequest.sendRequest(HttpRequest.PUT, URL.SHOP_CART_RESELECT_SKU, params, object : IDataSource.HttpRequestCallBack {
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