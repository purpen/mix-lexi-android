package com.thn.lexi.user.register

import java.io.Serializable

data class TokenBean(var success: Boolean, var data: DataBean, var status : StatusBean):Serializable {
    data class DataBean(var expiration:Long,var token:String)
    data class StatusBean(var code:Int,var message:String)
}