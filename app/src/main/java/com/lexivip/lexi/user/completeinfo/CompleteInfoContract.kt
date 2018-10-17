package com.lexivip.lexi.user.completeinfo

import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import org.json.JSONArray

class CompleteInfoContract {
    interface View : BaseView<Presenter>{
        fun showLoadingView()
        fun showError(s: String)
        fun goPage()
        fun dismissLoadingView()
        fun showInfo(string: String)
        fun setUploadAvatarData(ids: JSONArray)
        fun setUploadTokenData(uploadTokenBean: UploadTokenBean?)
    }
    interface Presenter : BasePresenter {
        /**
         * 上传头像
         */
        fun uploadAvatar(byteArray: UploadTokenBean?, byteArray1: ByteArray)

        /**
         * 上传用户信息
         */
        fun uploadUserInfo(avatar_id: String, name: String, birth: String, gender: String)
    }
}