package com.thn.lexi.user.password

data class ForgetPasswordBean(var success: Boolean, var status : StatusBean) {
    data class StatusBean(var code:Int,var message:String)
}