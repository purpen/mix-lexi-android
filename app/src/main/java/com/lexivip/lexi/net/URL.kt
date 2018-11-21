package com.lexivip.lexi.net

object URL {
    //测试服务器
//    const val BASE_URL = "https://wx.taihuoniao.com/v1.0/"
    //正式服务器
    const val BASE_URL = "https://wxapi.lexivip.com/v1.0/"

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

    /**store//app_visitor
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
     * 探索->全部特色品牌馆
     */
    const val ALL_FEATURE_BRAND_PAVILION = "${BASE_URL}column/feature_store_all"

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
    const val DISTRIBUTION_GOODS_LIST = "${BASE_URL}core_platforms/products/by_store"


    /**
     * 探索->集合
     */
    const val EXPLORE_WELL_GOODS_COLLECTION = "${BASE_URL}column/collections/basic"

    /**
     * 集合列表
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
     * 核心删除分销商品
     */
    const val DELETE_DISTRIBUTE_GOODS = "${BASE_URL}core_platforms/fx_distribute/remove"


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
    const val GOODS_CATEGORIES_URL = "${BASE_URL}official/categories"

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
     * 用户生活馆动态
     */
    const val USER_DYNAMIC_URL = "${BASE_URL}users/user_dynamic"

    /**
     * 每个SKU商品的快递列表
     */
    const val PRODUCT_EXPRESS = "${BASE_URL}logistics/product/express"

    /**
     *  根据商品运费模板获取快递
     */
    const val SAME_TEMPLATE_EXPRESS = "${BASE_URL}logistics/same_template_express"

    /**
     * 计算运费列表
     */
    const val FREIGHT_CALCULATE = "${BASE_URL}logistics/freight/calculate"
    /**
     * 根据订单价格获取官方优惠券
     */
    const val ORDER_OFFICIAL_COUPONS = "${BASE_URL}market/user_official_fill"
    /**
     * 购物车重新选择SKU
     */
    const val SHOP_CART_RESELECT_SKU = "${BASE_URL}cart/re_election"
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

    /**
     * 是否72小时内新用户首单
     */
    const val NEW_USER_FIRST_ORDER_DISCOUNTS = "${BASE_URL}market/coupons/new_user_discount"

    /**
     * 获每订单(店铺)的满减信息
     */
    const val PERORDER_FULL_REDUCTION = "${BASE_URL}market/user_order_full_reduction"


    /**
     * 获取店铺订单优惠券
     */
    const val PAVILION_ORDER_COUPONS = "${BASE_URL}market/user_order_coupons"


    /**
     * 提交并创建订单
     */
    const val SUBMIT_ORDER = "${BASE_URL}orders/create"

    /**
     * 全部省市区列表
     */
    const val PROVINCES_CITY_COUNTRY = "${BASE_URL}places/provinces_cities"

    /**
     * 订单全部列表
     */
    const val GET_ORDER = "${BASE_URL}orders"
    /**
     * 删除订单
     */
    const val DELETE_ORDER = "${BASE_URL}orders/delete"
    /**
     * 获取商品物流信息
     */
    const val GET_LOGISTICS = "${BASE_URL}logistics/information"

    /**
     * 获取用身份信息
     */
    const val GET_USER_IDENTITY = "${BASE_URL}address/custom"

    /**
     * 搜索商品
     */
    const val SEARCH_GOODS_URL = "${BASE_URL}core_platforms/search/products"

    /**
     * 搜索品牌馆
     */
    const val SEARCH_BRAND_PAVILION_URL = "${BASE_URL}core_platforms/search/stores"

    /**
     * 关注的品牌馆列表
     */
    const val FOCUSED_BRAND_PAVILION_URL = "${BASE_URL}users/followed_stores"


    /**
     * 搜索用户
     */
    const val SEARCH_USERS_URL = "${BASE_URL}core_platforms/search/users"


    /**
     * 订单确认收货
     */
    const val ORDER_FINISH = "${BASE_URL}orders/signed"

    /**
     *添加商品评价
     */
    const val ORDER_EVALUATE = "${BASE_URL}orders/user_comment/create"

    /**
     * 搜索->热门推荐品牌馆
     */
    const val SEARCH_HOT_RECOMMEND_PAVILION = "${BASE_URL}core_platforms/search/hot_recommend"

    /**
     * 搜索->热门搜索
     */
    const val SEARCH_HOT_URL = "${BASE_URL}core_platforms/search/week_hot"

    /**
     * 搜索 ->模糊匹配
     */
    const val FUZZY_WORD_SEARCH_URL = "${BASE_URL}core_platforms/search"

    /**
     * 查看全部编辑推荐
     */
    const val ALL_EDITOR_RECOMMEND = "${BASE_URL}column/explore_recommend"

    /**
     * 栏目编码: 编辑推荐=e_recommend, 优质精品=e_new, 特惠好设计=preferential_design, 百元好物=affordable_goods
     * 用户浏览记录
     */
    const val USER_BROWSE_RECORDS = "${BASE_URL}column/browse_records"

    /**
     * 精选品牌馆
     */

    const val SELECTION_BRAND_PAVILION = "${BASE_URL}column/handpick_store"

    /**
     * 集合详情
     */
    const val GOODS_COLLECTION_DETAIL = "${BASE_URL}column/collections/detail"


    /**
     *  优质新品
     */
    const val ALL_FEATURE_NEW_GOODS = "${BASE_URL}column/explore_new"

    /**
     * 商品分类
     */
    const val CLASSIFY_GOODS_URL = "${BASE_URL}category/products"

    /**
     * 获取小程序海报
     */
    const val MARKET_WXA_POSTER = "${BASE_URL}market/wxa_poster"


    /**
     * 生活馆订单统计
     */
    const val LIFE_ORDER_COLLECT = "${BASE_URL}stats/life_orders_collect"
    /**
     * 生活馆收益汇总
     */
    const val LIFE_ORDER_INCOME_COLLECT = "${BASE_URL}stats/life_orders_sale_collect"
    /**
     *生活馆提现汇总
     */
    const val LIFE_CASH_COLLECT = "${BASE_URL}stats/life_cash_collect"
    /**
     * 生活馆订单列表
     */
    const val LIFE_ORDER = "${BASE_URL}orders/life_orders"
    /**
     * 生活馆订单交易记录
     */
    const val LIFE_TRANSACTION = "${BASE_URL}stats/life_orders/transactions"
    /**
     * 最近一笔提现
     */
    const val LIFE_CASH_RECENT = "${BASE_URL}stats/life_cash_recent"
    /**
     * 对账单列表
     */
    const val LIFE_ACCOUNT_STATEMENT = "${BASE_URL}stats/life_orders/statements"
    /**
     * 对账单详情
     */
    const val LIFE_ACCOUNT_DETAIL = "${BASE_URL}stats/life_orders/statement_items"
    /**
     * 对账单收益详情
     */
    const val LIFE_ACCOUNT_ORDER = "${BASE_URL}stats/life_orders"

    /**
     *  接单订制
     */
    const val PRODUCTS_CUSTOM_MADE = "${BASE_URL}products/custom_made"

    /**
     * 上架商品
     */
    const val PUT_AWAY_GOODS = "${BASE_URL}core_platforms/fx_distribute/publish"
    /**
     * 获取品牌馆信息
     */
    const val BRAND_HOUSE = "${BASE_URL}official_store/info"
    /**
     * 获取品牌馆公告
     */
    const val BRAND_HOUSE_NOTICE = "${BASE_URL}official_store/announcement"
    /**
     * 店铺分类列表
     */
    const val BRAND_CATEGORIES = "${BASE_URL}store/categories"
    /**
     * 品牌馆生活志列表
     */
    const val BRAND_RECORD = "${BASE_URL}core_platforms/life_records"
    /**
     * 品牌馆主人信息
     */
    const val BRAND_OWNER = "${BASE_URL}official_store/master_info"
    /**
     * 品牌馆故事详情
     */
    const val BRAND_DETAIL = "${BASE_URL}official_store/detail"

    /**
     * 新发布商品
     */
    const val NEW_PUBLISH_PRODUCTS = "${BASE_URL}core_platforms/fx_distribute/latest"

    /**
     * 生活志列表
     */
    const val LIFE_WILL = "${BASE_URL}life_records"

    /**
     * 发现 ->猜你喜欢
     */
    const val DISCOVER_GUESS_LIKE = "${BASE_URL}life_records/guess_likes"

    /**
     * 发现 ->精彩故事
     */
    const val DISCOVER_WONDERFUL_STORY = "${BASE_URL}life_records/wonderful_stories"

    /**
     * 创作人故事频道
     */
    const val COMPOSER_STORY_LIST = "${BASE_URL}life_records/creator_story"

    /**
     * 种草清单频道
     */
    const val SEEDING_MANIFEST_LIST = "${BASE_URL}life_records/grass_note"

    /**
     * 生活记事频道
     */
    const val LIFE_RECORDS_LIST = "${BASE_URL}life_records/life_remember"

    /**
     * 手作教学频道
     */
    const val HAND_MADE_LIST = "${BASE_URL}life_records/hand_teach"

    /**
     * 生活志详情
     */
    const val LIFE_RECORDS_ARTICLE_DETAIL = "${BASE_URL}life_records/detail"

    /**
     * 相关故事推荐
     */
    const val LIFE_RECORDS_SIMILAR = "${BASE_URL}life_records/similar"

    /**
     * 文章详情推荐商品
     */
    const val LIFE_RECORDS_RECOMMEND_PRODUCTS = "${BASE_URL}life_records/recommend_products"

    /**
     * 精选->今日推荐
     */
    const val TODAY_RECOMMEND_URL = "${BASE_URL}column/daily_recommends"
    /**
     * 领券中心 官方优惠券
     */
    const val VOUCHER_RECOMMEND = "${BASE_URL}market/official_coupons/recommend"
    /**
     * 全部分类
     */
    const val ALL_CLASS="${BASE_URL}official/categories"
    /**
     * 领券中心头条动态
     */
    const val VOUCHER_LINES = "${BASE_URL}market/coupon_lines"

    /**
     * 评论列表
     */
    const val SHOW_WINDOW_COMMENTS_LIST: String = "${BASE_URL}shop_windows/comments"
    /**
     * 领券中心-同享券-精品品牌券
     */
    const val VOUCHER_SHARE="${BASE_URL}market/coupon_center_shared"
    /**
     * 领券中心-单享券-精选商品券
     */
    const val VOUCHER_SINGLE="${BASE_URL}market/coupon_center_single"
    /**
     * 领券中心活动图片
     */
    const val VOUCHER_IMAGE="${BASE_URL}coupon_ad"
    /**
     * 领取官方优惠券
     */
    const val VOUCHER_OFFICIAL_RECEIVE="${BASE_URL}market/official_coupons/grant"
    /**
     * 微信支付签名
     */
    const val WX_PAY="${BASE_URL}orders/app_pay"
    /**
     * 微信订单支付签名
     */
    const val WX_PAY_ORDER="${BASE_URL}orders/wx_pay/app"
    /**
     * 订单是否合并支付
     */
    const val IS_MERGE="${BASE_URL}orders/app_pay/is_merge"

    /**
     * 别人喜欢的商品
     */
   const val OTHERS_FAVORITE_GOODS = "${BASE_URL}other_userlike"

    /**
     * 别人喜欢的橱窗
     */
   const val OTHERS_FAVORITE_SHOW_WINDOW = "${BASE_URL}shop_windows/other_user_likes"

    /**
     * 别人的心愿单
     */
    const val OTHERS_USER_WISHLIST = "${BASE_URL}other_wishlist"

    /**
     * 别人最近查看的商品
     */
    const val OTHER_RECENT_LOOK_GOODS = "${BASE_URL}other_user_browses"

    /**
     * 别人关注
     */
    const val OTHER_FOCUSED_BRAND_PAVILION = "${BASE_URL}users/other_followed_stores"

    /**
     * 别人的个人中心
     */
    const val OTHERS_USER_CENTER="${BASE_URL}users/get_other_user_center"

    /**
     * 别人喜欢商品列表
     */
    const val OTHER_FAVORITE_GOODS = "${BASE_URL}other_userlike"

    /**
     * 别人最近查看商品列表
     */
    const val OTHERS_RECENT_LOOK_GOODS = "${BASE_URL}other_user_browses"
    /**
     * 分享品牌馆
     */
    const val SHARE_BRAND="${BASE_URL}market/share/store"
    /**
     * 分享生活馆
     */
    const val SHARE_LIFE="${BASE_URL}market/share/life_store"
    /**
     * 邀请好友开馆卡片
     */
    const val SHARE_INVITE="${BASE_URL}market/share/invite_carde"
    /**
     * 分享优惠券
     */
    const val SHARE_VOUCHER="${BASE_URL}market/share/coupons"

    /**
     * 包邮专区
     */
    const val GOODS_FREE_POSTAGE = "${BASE_URL}products/free_postage"

    /**
     * 热门橱窗标签
     */
    const val SHOP_WINDOW_HOT_TAGS = "${BASE_URL}shop_windows/hot_keywords"

    /**
     * 动态匹配橱窗标签
     */
    const val FUZZY_WORD_SEARCH_TAG = "${BASE_URL}shop_windows/search/keywords"

    /**
     * 添加标签
     */
    const val SHOP_WINDOW_KEYWORD = "${BASE_URL}shop_windows/keywords"

    /**
     * 选中的商品图片
     */
    const val SELECT_GOODS_IMAGE = "${BASE_URL}products/images"

    /**
     * 发布橱窗
     */
    const val PUBLISH_SHOP_WINDOW = "${BASE_URL}shop_windows"

    /**
     * 包含指定标签的橱窗
     */
    const val RELATE_SHOW_WINDOW = "${BASE_URL}shop_windows/keyword"

    /**
     * 保存支付方式
     */
    const val SAVE_PAY_WAY = "${BASE_URL}orders/pay_type"
}
