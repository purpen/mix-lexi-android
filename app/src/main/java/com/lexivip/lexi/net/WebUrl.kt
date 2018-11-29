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
     * 小程序分享商品
     */
    const val AUTH_GOODS="pages/product/product?rid="
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
     * 别人的主页
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
}