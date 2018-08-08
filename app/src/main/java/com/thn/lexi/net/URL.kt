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
    const val LOGIN_URL = "${BASE_URL}auth/login"

    /**
     * 登录发送验证码
     */
    const val LOGIN_SEND_CHECKCODE: String = "${BASE_URL}users/dynamic_login_verify_code"

    /**
     *  忘记密码发送验证码
     */
    const val FORGET_PASSWORD_SEND_CHECKCODE = "${BASE_URL}users/find_pwd_verify_code"

    /**
     *  忘记密码校验验证码
     */
    const val FORGET_PASSWORD_VERIFY_CHECKCODE = "${BASE_URL}auth/find_pwd"

    /**
     * 忘记密码后设置密码
     */
    const val FORGET_PASSWORD_SET_NEW = "${BASE_URL}auth/modify_pwd"

    /**
     * 使用动态码登录
     */
    val LOGIN_WITH_CHECKCODE = "${BASE_URL}auth/app_dynamic_login"


    /*
     *注册验证码URL
     */
    const val REGISTER_VERIFY_CODE = "${BASE_URL}users/register_verify_code"

    /*
    *注册设置密码
    */
    const val REGISTER_SET_PASSWORD = "${BASE_URL}auth/set_password"

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
    const val APPKEY_APPSECRET = "${BASE_URL}auth/exchange_token"


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
    const val UPLOAD_TOKEN = "${BASE_URL}assets/user_upload_token"

    /**
     * 首次登录完善资料
     */
    const val COMPLETE_USER_INFO = "${BASE_URL}auth/complete_info"

    /**
     * 探索->商品分类
     */
    const val GOODS_CLASS_URL = "${BASE_URL}categories"

    /**
     * 探索Banner图
     */
    const val EXPLORE_BANNER_URL = "${BASE_URL}banners/explore"

    /**
     * 探索->编辑推荐
     */
    const val EDITOR_RECOMMEND_URL = "${BASE_URL}column/explore_recommend"

    /**
     * 探索->优质新品
     */
    const val FEATURE_NEW_GOODS = "${BASE_URL}column/explore_new"

    /**
     * 探索->店铺取消关注
     */
    const val UNFOCUS_BRAND_PAVILION = "${BASE_URL}unfollow/store"

    /**
     * 探索->店铺关注
     */
    const val FOCUS_BRAND_PAVILION = "${BASE_URL}follow/store"

    /**
     * 精选->Banner
     */
    const val SELECTION_BANNER_URL = "${BASE_URL}banners/handpick"

    /**
     * 精选->品牌馆
     */
    const val BRAND_PAVILION_URL = "${BASE_URL}column/feature_store"

    /**
     * 精选人气推荐banner
     */
    const val SELECTION_HOT_PEOPLE_BANNER = "${BASE_URL}banners/handpick_content"

    /**
     * 小b分销商品列表
     */
    const val DISTRIBUTION_GOODS_LIST = "${BASE_URL}fx_distribute/proprietary"

    /**
     * 探索->集合
     */
    const val WELL_GOODS_COLLECTION= "${BASE_URL}column/collections"

    /**
     * 探索->特惠好设计
     */
    const val GOOD_DESIGN_URL = "${BASE_URL}column/preferential_design"

    /**
     * 探索->百元好物
     */
    const val GOOD100_URL = "${BASE_URL}column/affordable_goods"
}