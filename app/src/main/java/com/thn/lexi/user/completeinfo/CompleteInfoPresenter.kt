package com.thn.lexi.user.completeinfo

import android.text.TextUtils
import com.basemodule.tools.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import org.json.JSONArray
import java.io.IOException

class CompleteInfoPresenter(view: CompleteInfoContract.View) : CompleteInfoContract.Presenter {

    private var view: CompleteInfoContract.View = checkNotNull(view)

    private val dataSource: CompleteInfoModel by lazy { CompleteInfoModel() }

    /**
     * 获取上传token
     */
    fun getUploadToken() {
        dataSource.getUploadToken(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val uploadTokenBean = JsonUtil.fromJson(json, UploadTokenBean::class.java)
                if (uploadTokenBean.success) {
                    view.setUploadTokenData(uploadTokenBean)
                } else {
                    view.showInfo(uploadTokenBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 上传图片
     */
    override fun uploadAvatar(uploadTokenBean: UploadTokenBean?, byteArray: ByteArray) {
        if (uploadTokenBean==null) {
            ToastUtil.showInfo(R.string.text_net_error)
            return
        }

        dataSource.uploadAvatar(byteArray,uploadTokenBean,object : IDataSource.UpLoadCallBack {
            override fun onComplete(ids: JSONArray) {
                LogUtil.e("uploadAvatar===上传完成，图片id=${ids[0]}")
                view.setUploadAvatarData(ids)
            }
        })
    }


    /**
     * 上传用户信息
     */
    override fun uploadUserInfo(avatar_id: String, name: String, birth: String, gender: String) {
        if (TextUtils.isEmpty(avatar_id)){
            ToastUtil.showInfo(AppApplication.getContext().getString(R.string.hint_text_upload_avatar))
            return
        }

        if (TextUtils.isEmpty(name)){
            ToastUtil.showInfo(AppApplication.getContext().getString(R.string.hint_input_user_name))
            return
        }

        dataSource.uploadUserInfo(avatar_id, name, birth, gender, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val completeInfoBean = JsonUtil.fromJson(json, CompleteInfoBean::class.java)
                if (completeInfoBean.success) {
                    view.goPage()
                } else {
                    view.showInfo(completeInfoBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}