package com.thn.lexi.net

object URL {
    const val BASE_URL = "https://fx.taihuoniao.com/api/v1.0/"

    /**
     * 注册
     */
    const val REGISTER_URL = "${BASE_URL}auth/register"

    /**
     * 请求Token
     */
    const val TOKEN_URL = "${BASE_URL}token"

    /**
     *登录
     */
    const val LOGIN_URL = "${BASE_URL}auth/business_login"

    /**
     * 商品列表
      */
    const val GOODS_LIST_URL = "${BASE_URL}products"

    /**
     * 用户信息
      */
    const val USER_INFO_URL = "${BASE_URL}users"


    /**
     * 换取token
     */
    const val APPKEY_APPSECRET ="${BASE_URL}auth/exchange_token"


    /**
     * 相似商品
     */
    const val SIMILAR_GOODS_URL ="${BASE_URL}products/sticked"

}