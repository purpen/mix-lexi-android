package com.lexivip.lexi.user.password

data class ForgetPasswordBean(var success: Boolean, var status : StatusBean,var data:DataBean) {
    data class StatusBean(var code:Int,var message:String)
    data class DataBean(var areacode:String,var email:String)
}