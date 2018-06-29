package com.thn.lexi.user.setting

data class UserInfoBean(var success: Boolean, var data: DataBean, var status : StatusBean) {

    data class DataBean(var email:String,var last_seen:Long,var uid:String,var username:String)

    data class StatusBean(var code:Int,var message:String)
}
