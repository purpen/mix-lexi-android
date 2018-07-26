package com.thn.lexi.net

object URL {
    const val BASE_URL = "https://wx.taihuoniao.com/api/v1.0/"

    /**
     * 注册
     */
    const val REGISTER_URL = "${BASE_URL}auth/app_register"

    /**
     * 请求Token
     */
    const val TOKEN_URL = "${BASE_URL}token"

    /**
     *登录
     */
    const val LOGIN_URL = "${BASE_URL}auth/business_login"

    /**
     *登录/忘记密码验证码URL
     */
    const val LOGIN_FORGET_VERIFY_CODE = "${BASE_URL}users/login_verify_code"

    /*
     *注册验证码URL
     */
    const val REGISTER_VERIFY_CODE = "${BASE_URL}users/register_verify_code"

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
    const val SIMILAR_GOODS_URL = "${BASE_URL}products/sticked"

    /**
     * 喜欢商品
     */
    const val FAVORITE_GOODS_URL = "${BASE_URL}userlike"

    /**
     *商品SKU
     */
    const val SKU_LIST = "${BASE_URL}products/skus"

    /**
     * 手机号地区编码
     */
    const val AREA_CODE_URL = "${BASE_URL}auth/area_code"

    /**
     * 获取图片上传token
     */
    const val UPLOAD_TOKEN = "${BASE_URL}assets/up_token"

    /**
     * 首次登录完善资料
     */
    const val COMPLETE_USER_INFO = "${BASE_URL}auth/info"

    /**
     * 探索->商品分类
     */
    const val GOODS_CLASS_URL = "${BASE_URL}categories"

    /**
     * 探索Banner图
     */
    const val EXPLORE_BANNER_URL = "${BASE_URL}banners/explore"

}