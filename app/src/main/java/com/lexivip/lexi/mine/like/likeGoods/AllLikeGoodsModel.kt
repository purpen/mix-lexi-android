package com.lexivip.lexi.mine.like.likeGoods

import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException

open class AllLikeGoodsModel {

    fun loadData(page: Int, sortType: String, minePrice: String, maxPrice: String, cids: String,is_free_postage: String, is_preferential: String,is_custom_made: String,sort_newest:String,callBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getAllEditorRecommendParams(page, sortType,minePrice, maxPrice, cids,is_free_postage,is_preferential,is_custom_made,sort_newest)

        HttpRequest.sendRequest(HttpRequest.GET, URL.FAVORITE_GOODS_URL, params, object : IDataSource.HttpRequestCallBack {
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
     * 加载别人喜欢数据
     */
    fun loadData(uid:String,page:Int,callBack: IDataSource.HttpRequestCallBack){
        val params = ClientParamsAPI.getDefaultParams()
        params["uid"] = uid
        params["page"] = "$page"
        HttpRequest.sendRequest(HttpRequest.GET, URL.OTHER_FAVORITE_GOODS, params, object : IDataSource.HttpRequestCallBack {
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
        HttpRequest.sendRequest(HttpRequest.GET, URL.OTHER_FAVORITE_GOODS, params, object : IDataSource.HttpRequestCallBack {
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


