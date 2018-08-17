package com.thn.lexi.selectionGoodsCenter

import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class AllGoodsModel{



    fun loadData(page: Int,sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String, callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getAllGoodsParams(page,sortType,profitType,filterCondition,minePrice,maxPrice)

        HttpRequest.sendRequest(HttpRequest.GET,URL.ALL_GOODS_URL,params,object : IDataSource.HttpRequestCallBack{
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

    /**
     * 获取商品分类
     */
    fun getGoodsClassify(callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET,URL.GOODS_CATEGORIES_URL,params,object : IDataSource.HttpRequestCallBack{
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


