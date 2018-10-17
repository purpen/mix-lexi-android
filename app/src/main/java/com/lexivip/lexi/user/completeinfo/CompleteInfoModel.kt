package com.lexivip.lexi.user.completeinfo

import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.net.ClientParamsAPI
import com.lexivip.lexi.net.HttpRequest
import com.lexivip.lexi.net.URL
import java.io.IOException
import com.qiniu.android.storage.*
import com.lexivip.lexi.AppApplication
import java.util.HashMap


open class CompleteInfoModel {

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


    fun uploadAvatar(byteArray: ByteArray, uploadTokenBean: UploadTokenBean, callback: IDataSource.UpLoadCallBack) {

        val token =  uploadTokenBean.data.up_token

        val map = HashMap<String, String>()
        map["x:user_id"] = uploadTokenBean.data.user_id
        map["x:directory_id"] = uploadTokenBean.data.directory_id

        val uploadOptions = UploadOptions(map, "image/jpeg", false, null, null)

        AppApplication.getUploadManager().put(byteArray, null, token,
                { key, info, res ->
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if (info.isOK) {
                        callback.onComplete(res.getJSONArray("ids"))
                        LogUtil.i("CompleteInfoModel","qiniu Upload Success")
                    } else {
                        LogUtil.i("CompleteInfoModel","qiniu Upload Fail")
                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    }

                    LogUtil.i("CompleteInfoModel", "$key,\r\n $info,\r\n $res")
                }, uploadOptions)
    }


    fun uploadUserInfo(avatar_id: String, name: String, birth: String, gender: String, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getUploadUserInfoParams(avatar_id,name,birth,gender)
        HttpRequest.sendRequest(HttpRequest.POST,URL.COMPLETE_USER_INFO,params, object : IDataSource.HttpRequestCallBack {
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

