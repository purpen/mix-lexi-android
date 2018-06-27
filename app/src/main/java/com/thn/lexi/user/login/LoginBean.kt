package com.thn.lexi.user.login

data class LoginBean(var success: Boolean, var data: DataBean, var status : StatusBean) {

    data class DataBean(var created_at:Long,var expiration:Long,var token:String)

    data class StatusBean(var code:Int,var message:String)
}
