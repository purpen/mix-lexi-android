package com.lexivip.lexi.index.lifehouse

import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import com.qiniu.android.storage.UploadOptions
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import com.lexivip.lexi.user.completeinfo.UploadTokenBean
import com.lexivip.lexi.user.login.UserProfileUtil
import java.io.IOException
import java.util.HashMap

open class LifeHouseModel {
    companion object {
        //出售中
        const val STATUS: String = "1"

        //是否获取用户喜欢，心愿单操作记录
        const val USER_RECORD = "1"
    }

    fun loadData(cid: String, page: Int, callBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getGoodListParams(cid, page, STATUS, USER_RECORD)

        HttpRequest.sendRequest(HttpRequest.GET, URL.DISTRIBUTION_GOODS_LIST, params, object : IDataSource.HttpRequestCallBack {
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


    fun getWelcomeInWeek(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getGoodSelectionParams()
        HttpRequest.sendRequest(HttpRequest.GET, URL.WELCOME_IN_WEEK, params, object : IDataSource.HttpRequestCallBack {
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

    fun getLookPeople(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        val storeId = UserProfileUtil.storeId()
        val url = URL.BASE_URL + "store/$storeId/app_visitor"
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

    fun getLifeHouse(httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getLifeStoreParams()

        HttpRequest.sendRequest(HttpRequest.GET, URL.SMALL_LIFE_STORE, params, object : IDataSource.HttpRequestCallBack {
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

    fun editLifeHouse(title: String, description: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getEditLifeStoreParams(title, description)

        HttpRequest.sendRequest(HttpRequest.POST, URL.EDIT_SMALL_LIFE_STORE, params, object : IDataSource.HttpRequestCallBack {
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

    fun deleteDistributeGoods(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDeleteDistributeGoodsParams(rid)

        HttpRequest.sendRequest(HttpRequest.DELETE, URL.DELETE_DISTRIBUTE_GOODS, params, object : IDataSource.HttpRequestCallBack {
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
     * 取消喜欢
     * @param rid 商品id
     * @param httpRequestCallBack
     */
    fun unfavoriteGoods(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFavoriteGoodsParams(rid)
        HttpRequest.sendRequest(HttpRequest.DELETE,URL.FAVORITE_GOODS_URL,params,object : IDataSource.HttpRequestCallBack{
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
    fun favoriteGoods(rid: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getFavoriteGoodsParams(rid)
        HttpRequest.sendRequest(HttpRequest.POST, URL.FAVORITE_GOODS_URL,params,object : IDataSource.HttpRequestCallBack{
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
     * 获取上传token
     */
    fun getUploadToken(callback: IDataSource.HttpRequestCallBack) {

        val params = ClientParamsAPI.getDefaultParams()

        HttpRequest.sendRequest(HttpRequest.GET,URL.UPLOAD_TOKEN,params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callback.onStart()
            }

            override fun onSuccess(json: String) {
                callback.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callback.onFailure(e)
            }
        })
    }

    fun uploadLifeHouseLogo(byteArray: ByteArray, uploadTokenBean: UploadTokenBean, upLoadCallBack: IDataSource.UpLoadCallBack) {
        val token =  uploadTokenBean.data.up_token

        val map = HashMap<String, String>()
        map["x:user_id"] = uploadTokenBean.data.user_id
        map["x:directory_id"] = uploadTokenBean.data.directory_id

        val uploadOptions = UploadOptions(map, "image/jpeg", false, null, null)

        AppApplication.getUploadManager().put(byteArray, null, token,
                { key, info, res ->
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if (info.isOK) {
                        upLoadCallBack.onComplete(res.getJSONArray("ids"))
                        LogUtil.i("CompleteInfoModel","qiniu Upload Success")
                    } else {
                        LogUtil.i("CompleteInfoModel","qiniu Upload Fail")
                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    }

                    LogUtil.i("CompleteInfoModel", "$key,\r\n $info,\r\n $res")
                }, uploadOptions)
    }


    fun uploadLifeHouseLogoId(logoId: String, callback: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getUploadLifeHouseLogoIdParams(logoId)
        HttpRequest.sendRequest(HttpRequest.PUT,URL.UPLOAD_LIFE_HOUSE_LOGO_ID,params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callback.onStart()
            }

            override fun onSuccess(json: String) {
                callback.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callback.onFailure(e)
            }
        })
    }

    fun getNewPublishProducts(callback: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        HttpRequest.sendRequest(HttpRequest.GET,URL.NEW_PUBLISH_PRODUCTS,params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callback.onStart()
            }

            override fun onSuccess(json: String) {
                callback.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callback.onFailure(e)
            }
        })
    }


}


