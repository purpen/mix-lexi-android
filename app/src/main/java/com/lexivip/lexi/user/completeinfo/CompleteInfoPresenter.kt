package com.lexivip.lexi.user.completeinfo

import android.text.TextUtils
import com.lexivip.lexi.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
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
        if (uploadTokenBean == null) {
            ToastUtil.showInfo(R.string.text_net_error)
            return
        }

        dataSource.uploadAvatar(byteArray, uploadTokenBean, object : IDataSource.UpLoadCallBack {
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
        if (TextUtils.isEmpty(avatar_id)) {
            ToastUtil.showInfo(AppApplication.getContext().getString(R.string.hint_text_upload_avatar))
            return
        }

        if (TextUtils.isEmpty(name)) {
            ToastUtil.showInfo(Util.getString(R.string.hint_input_user_name))
            return
        }

        if (name.length < 2) {
            ToastUtil.showInfo(Util.getString(R.string.hint_input_user_name_less_than_2))
            return
        }

        dataSource.uploadUserInfo(avatar_id, name, birth, gender, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
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