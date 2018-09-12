package com.thn.lexi.net

object URL {
    const val BASE_URL = "https://wx.taihuoniao.com/v1.0/"

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
     * 精选->人气推荐
     */
    const val PEOPLE_HOT_RECOMMEND = "${BASE_URL}column/handpick_recommend"

    /**
     * 精选->发现生活美学
     */
    const val DISCOVER_LIFE_URL = "${BASE_URL}shop_windows/recommend"

    /**
     * 精选->乐喜优选
     */
    const val GOOD_SELECTION_URL = "${BASE_URL}column/handpick_optimization"

    /**
     * 精选->种草清单
     */
    const val ZC_MANIFEST_URL = "${BASE_URL}life_records/recommend"


    /**
     * 小b分销商品列表
     */
    const val DISTRIBUTION_GOODS_LIST = "${BASE_URL}fx_distribute/agency"

    /**
     * 探索->集合
     */
    const val WELL_GOODS_COLLECTION = "${BASE_URL}column/collections"

    /**
     * 探索->特惠好设计
     */
    const val GOOD_DESIGN_URL = "${BASE_URL}column/preferential_design"

    /**
     * 探索->百元好物
     */
    const val GOOD100_URL = "${BASE_URL}column/affordable_goods"

    /**
     * 获取小B生活馆信息
     */
    const val SMALL_LIFE_STORE = "${BASE_URL}store/life_store"

    /**
     * 编辑生活馆
     */
    const val EDIT_SMALL_LIFE_STORE = "${BASE_URL}store/edit_store"

    /**
     * 删除分销商品
     */
    const val DELETE_DISTRIBUTE_GOODS = "${BASE_URL}fx_distribute/remove"


    /**
     * 头条信息
     */
    const val STORE_HEADLINE_URL = "${BASE_URL}store/store_headline"

    /**
     * 上传生活馆logoId
     */
    const val UPLOAD_LIFE_HOUSE_LOGO_ID = "${BASE_URL}store/update_life_store_logo"

    /**
     * 生活馆->本周最受欢迎
     */
    const val WELCOME_IN_WEEK = "${BASE_URL}fx_distribute/week_popular"


    /**
     * 用户信息
     */
    const val USER_PROFILE_URL = "${BASE_URL}users/profile"

    /**
     * 选品中心->热门单品
     */
    const val HOT_GOODS_URL: String = "${BASE_URL}fx_distribute/hot"

    /**
     * 选品中心->官方推荐
     */
    const val OFFICIAL_RECOMMEND_URL = "${BASE_URL}fx_distribute/sticked"

    /**
     * 选品中心->新品首发
     */
    const val FIRST_PUBLISH_URL = "${BASE_URL}fx_distribute/latest"

    /**
     * 选品中心 ->全部商品
     */
    const val ALL_GOODS_URL = "${BASE_URL}fx_distribute/choose_center"

    /**
     * 选品中心 -> 筛选->分类
     */
    const val GOODS_CATEGORIES_URL = "${BASE_URL}categories"

    /**
     *  发现生活美学 -> 橱窗推荐
     */
    const val RECOMMEND_SHOW_WINDOW = "${BASE_URL}shop_windows/recommend"


    /**
     * 发现生活美学 -> 喜欢橱窗
     */
    const val FAVORITE_SHOW_WINDOW = "${BASE_URL}shop_windows/user_likes"


    /**
     * 发现生活美学 -> 关注的橱窗
     */
    const val FOCUS_SHOW_WINDOW = "${BASE_URL}shop_windows/follow"


    /**
     * 关注用户
     */
    const val FOCUS_USER_URL = "${BASE_URL}follow/user"

    /**
     * 取消关注用户
     */
    const val UNFOCUS_USER_URL = "${BASE_URL}unfollow/user"


    /**
     * 橱窗详情
     */
    const val SHOW_WINDOW_DETAIL = "${BASE_URL}shop_windows/detail"

    /**
     * 发送橱窗评论
     */
    const val SHOP_WINDOWS_COMMENTS = "${BASE_URL}shop_windows/comments"


    /**
     * 对评论点赞
     */
    const val SHOP_WINDOWS_COMMENTS_PRAISE = "${BASE_URL}shop_windows/comments/praises"


    /**
     *  加载子评论
     */
    const val SHOP_WINDOWS_SUB_COMMENTS = "${BASE_URL}shop_windows/child_comments"


    /**
     * 相关橱窗
     */
    const val SHOP_WINDOWS_SIMILAR = "${BASE_URL}shop_windows/similar"

    /**
     * 猜你喜欢
     */
    const val SHOP_WINDOWS_GUESS_LIKE = "${BASE_URL}shop_windows/guess_like"

    /**
     * 用户个人中心
     */
    const val USER_CENTER = "${BASE_URL}users/user_center"

    /**
     *  用户个人最近浏览商品
     */
    const val RECENT_LOOK_GOODS = "${BASE_URL}user_browses"

    /**
     * 用户个人心愿单
     */
    const val WISH_ORDER = "${BASE_URL}wishlist"

    /**
     * 用户关注的设计馆
     */
    const val FOLLOWED_STORES = "${BASE_URL}users/followed_stores"

    /**
     * 用户生活馆动态
     */
    const val USER_DYNAMIC_URL = "${BASE_URL}users/user_dynamic"


    /**
     * 获取品牌馆中商品列表
     */
    const val GET_PRODUCTS_BY_STORE = "${BASE_URL}core_platforms/products/by_store"


    /**
     * 获取官方品牌馆信息
     */
    const val OFFICIAL_STORE_INFO = "${BASE_URL}official_store/info"

    /**
     *  获取登录用户优惠券列表
     */
    const val SHOP_STORE_LOGIN_COUPONS = "${BASE_URL}market/user_master_coupons"

    /**
     *  获取未登录用户优惠券列表
     */
    const val SHOP_STORE_UNLOGIN_COUPONS = "${BASE_URL}market/not_login_coupons"

    /**
     * 点击领取优惠券
     */
    const val CLICK_GET_COUPON = "${BASE_URL}market/coupons/grant"

    /**
     * 获取商品所有SKU
     */
    const val GET_GOODS_SKUS = "${BASE_URL}products/many_skus"

    /**
     *  添加心愿单
     */
    const val ADD_WISH_ORDER = "${BASE_URL}wishlist"

    /**
     * 获取相似商品
     */
    const val GET_SIMILAR_GOODS = "${BASE_URL}products/similar"

    /**
     * 获取喜欢商品的用户
     */
    const val GOODS_FAVORITE_USERS = "${BASE_URL}product/userlike"

    /**
     * 加入购物车
     */
    const val ADD_SHOP_CART = "${BASE_URL}cart"

    /**
     * 获取购物车商品数
     */
    const val SHOP_CART_ITEM_COUNT = "${BASE_URL}cart/item_count"

    /**
     * 获取购物车所有商品
     */
    const val SHOP_CART_GOODS = "${BASE_URL}cart"

    /**
     *  购物车移除商品SKU
     */
    const val REMOVE_FROM_SHOP_CART = "${BASE_URL}cart/remove"


    /**
     * 获取用户收货地址
     */
    const val GET_USER_EXPRESS_ADDRESS = "${BASE_URL}address"

}