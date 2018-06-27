package com.thn.lexi.user.register

data class RegisterBean(var success: Boolean,var status : StatusBean) {
    data class StatusBean(var code:Int,var message:String)
}