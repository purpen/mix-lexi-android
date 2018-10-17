package com.lexivip.lexi.selectionGoodsCenter

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import com.lexivip.lexi.user.login.UserProfileUtil
import java.io.IOException

open class AllGoodsModel{

    companion object { //只查询出售中的商品
        const val STATUS = "1"
    }

    fun loadData(page: Int,sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String,cids: String, callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getAllGoodsParams(page,sortType,profitType,filterCondition,STATUS,minePrice,maxPrice,cids)
        params?.set("sid", UserProfileUtil.storeId())
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


