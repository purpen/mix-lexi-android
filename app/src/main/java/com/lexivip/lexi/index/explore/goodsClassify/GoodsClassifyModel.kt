package com.lexivip.lexi.index.explore.goodsClassify

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class GoodsClassifyModel {

    fun loadData(page: Int, sortType: String, minePrice: String, maxPrice: String, cids: String,is_free_postage: String, is_preferential: String,is_custom_made: String,sort_newest:String,id:String,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getGoodsClassifyParams(page, sortType,minePrice, maxPrice, cids,is_free_postage,is_preferential,is_custom_made,sort_newest,id)

        HttpRequest.sendRequest(HttpRequest.GET, URL.CLASSIFY_GOODS_URL, params, object : IDataSource.HttpRequestCallBack {
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
    fun getGoodsClassify(pid:String,callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params.put("pid",pid)
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_CATEGORIES_URL, params, object : IDataSource.HttpRequestCallBack {
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


