package com.thn.lexi.index.detail

import com.basemodule.ui.IDataSource
import com.thn.lexi.mine.AdapterMineFavorite
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import com.thn.lexi.user.login.UserProfileUtil
import java.io.IOException

class GoodsDetailModel : IDataSource {

    fun loadData(goodsId: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getGoodsDetailData()
        HttpRequest.sendRequest(HttpRequest.GET, URL.BASE_URL + "products/$goodsId/all_detail", params, object : IDataSource.HttpRequestCallBack {
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


    fun loadBrandPavilionInfo(store_rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.loadBrandPavilionInfoParams(store_rid)
        HttpRequest.sendRequest(HttpRequest.GET, URL.OFFICIAL_STORE_INFO, params, object : IDataSource.HttpRequestCallBack {
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


    fun getExpressTime(rid: String, store_rid: String, goodsId: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getExpressTimeParams(goodsId, store_rid)

        val url = URL.BASE_URL + "logistics/core_freight_template/$rid"

        HttpRequest.sendRequest(HttpRequest.GET, url, params, object : IDataSource.HttpRequestCallBack {
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

    fun getSimilarGoods(goodsId: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getSimilarGoodsParams(goodsId)
        HttpRequest.sendRequest(HttpRequest.GET, URL.GET_SIMILAR_GOODS, params, object : IDataSource.HttpRequestCallBack {
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

    /**
     * 根据用户是否登录调不同接口
     */
    fun getCouponsByStoreId(store_rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getCouponsByStoreIdParams(store_rid)
        val url: String
        if (UserProfileUtil.isLogin()) {
            url = URL.SHOP_STORE_LOGIN_COUPONS
        } else {
            url = URL.SHOP_STORE_UNLOGIN_COUPONS
        }
        HttpRequest.sendRequest(HttpRequest.GET, url, params, object : IDataSource.HttpRequestCallBack {
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

    fun clickGetCoupon(storeId: String, code: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getClickCouponsParams(storeId, code)

        HttpRequest.sendRequest(HttpRequest.POST, URL.CLICK_GET_COUPON, params, object : IDataSource.HttpRequestCallBack {
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

    fun addWishOrder(goodsId: String, isAddWish: Boolean, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getGoodsIdParams(goodsId)

        val method: String
        if (isAddWish) {
            method = HttpRequest.POST
        } else {
            method = HttpRequest.DELETE
        }

        HttpRequest.sendRequest(method, URL.ADD_WISH_ORDER, params, object : IDataSource.HttpRequestCallBack {
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


    /**
     * 喜欢商品
     * @param rid 商品id
     * @param httpRequestCallBack
     */
    fun favoriteGoods(rid: String, favorite: Boolean, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFavoriteGoodsParams(rid)
        val method: String
        if (favorite) {
            method = HttpRequest.POST
        } else {
            method = HttpRequest.DELETE
        }
        HttpRequest.sendRequest(method, URL.FAVORITE_GOODS_URL, params, object : IDataSource.HttpRequestCallBack {
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