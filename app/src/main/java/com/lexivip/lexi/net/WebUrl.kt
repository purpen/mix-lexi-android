package com.lexivip.lexi.net

object WebUrl{
    const val BASE_URL="https://h5.lexivip.com/"
    /**
     * 生活馆
     */
    const val LIFE="${BASE_URL}article/life?rid="
    /**
     * 文章
     */
    const val GRASS="${BASE_URL}article/grass?rid="
    /**
     * 个人中心
     */
    const val USER="${BASE_URL}user/home?uid="
    /**
     * 商品链接
     */
    const val GOODS="${BASE_URL}product_view?rid="
    /**
     * 橱窗链接
     */
    const val WINDOW="${BASE_URL}shop/window?rid="
    /**
     * 加载35元邀请好友
     */
    const val INVITATION="${BASE_URL}invitation?uid=14892617530"//https://h5.lexivip.com/invitation?uid=14892617530
    /**
     * 35元邀请好友分享链接
     */
    const val SHARE_INVITATION="${BASE_URL}redenvelope?uid="
    /**
     * 品牌馆链接
     */
    const val BRAND_HOUSE="${BASE_URL}brand_pavilion?rid="
    /**
     * 集合链接
     */
    const val COLLECTION="${BASE_URL}collection?id="
    /**
     * 隐私条款
     */
    const val PRIVACY="${BASE_URL}site/privacy"
    /**
     * 服务条款
     */
    const val SERVICE="${BASE_URL}site/service_agreement"
    /**
     * 开馆指引（邀请好友开馆）
     */
    const val OPEN_SHOP="https://h5.lexivip.com/shop/guide"
    /**
     * 资质信息
     */
    const val QUALIFICAIONS="${BASE_URL}shop/qualification_validation?rid="
    /**
     * 小程序分享商品
     */
    const val AUTH_GOODS="pages/product/product"//?rid=
    /**
     * 小程序分享生活志的文章
     */
    const val AUTH_ARTICLE="pages/findInfo/findInfo?rid="
    /**
     * 小程序分享生活志的文章（带商品的）
     */
    const val AUTH_ARTICLE_GOODS="pages/plantNoteInfo/plantNoteInfo?rid="
    /**
     * 小程序分享橱窗
     */
    const val AUTH_WINDOW="pages/windowDetail/windowDetail?windowRid="
    /**
     * 小程序生活馆
     */
    const val AUTH_LIFE="pages/index/index"
    /**
     * 小程序别人的主页
     */
    const val AUTH_PAGE="pages/people/people?uid="
    /**
     * 小程序设计馆
     */
    const val AUTH_BRAND="pages/branderStore/branderStore?rid="
    /**
     * 小程序集合
     */
    const val AUTH_GATHER="pages/gatherInfo/gatherInfo?rid="
    /**
     * 小程序开馆指引
     */
    const val AUTH_GUIDE="lifeStore/pages/lifeStoreGuide/lifeStoreGuide"
}