package com.thn.lexi.search
import com.basemodule.ui.IDataSource
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

open class SearchGoodsModel{

    companion object { //只查询出售中的商品
       const val STATUS = "1"
    }

    fun loadData(page: Int,sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String, callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getAllGoodsParams(page,sortType,profitType,filterCondition,STATUS,minePrice,maxPrice)

        HttpRequest.sendRequest(HttpRequest.GET,URL.SEARCH_GOODS_URL,params,object : IDataSource.HttpRequestCallBack{
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


