package com.lexivip.lexi.index.explore.goodsClassify
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import java.io.IOException

class GoodsClassifyPresenter(view: GoodsClassifyContract.View) : GoodsClassifyContract.Presenter {
    private var view: GoodsClassifyContract.View = checkNotNull(view)

    private val dataSource: GoodsClassifyModel by lazy { GoodsClassifyModel() }

    private var sortType: String = SORT_TYPE_SYNTHESISE

    private var minePrice: String = ""

    private var maxPrice: String = ""

    private var cids:String = ""

    //分类编号
    private var id:String = ""

    private var  isRefresh =false

    //0=全部, 1=包邮
    private var isFreePostage:String = "0"

    //是否特惠: 0=全部, 1=特惠
    private var isPreferential:String = "0"

    //是否可定制: 0=全部, 1=可定制
    private var isCustomMade:String = "0"

    //是否按最新排序: 0=否, 1=是
    private var sortNewest:String = "0"

    private var curPage: Int = 1

    companion object {
        //默认排序
        const val SORT_TYPE_DEFAULT: String = "0"

        //综合排序
        const val SORT_TYPE_SYNTHESISE: String = "1"

        //价格由低到高
        const val SORT_TYPE_LOW_UP: String = "2"

        //价格由高到低
        const val SORT_TYPE_UP_LOW: String = "3"
    }

    /**
     * 获取排序类型
     */
    fun getSortType(): String {
        return this.sortType
    }

    /**
     * 获取分类Id
     */
    fun getClassifyId(): String {
        return this.id
    }

    /**
     * 默认参数加载数据
     */
    override fun loadData(isRefresh: Boolean,id:String) {
        this.isRefresh = isRefresh
        if (isRefresh) this.curPage = 1
        loadData(curPage, sortType,minePrice, maxPrice,cids,isFreePostage,isPreferential,isCustomMade,sortNewest,id)
    }

    /**
     * 根据用户选择条件搜索
     */
    override fun loadData(page: Int, sortType: String, minePrice: String, maxPrice: String, cids: String, is_free_postage: String, is_preferential: String, is_custom_made: String,sort_newest: String,id:String) {
        this.curPage = page
        this.sortType = sortType
        this.minePrice = minePrice
        this.maxPrice = maxPrice
        this.cids = cids
        this.isFreePostage = is_free_postage
        this.isPreferential = is_preferential
        this.isCustomMade = is_custom_made
        this.sortNewest = sort_newest
        this.id = id
        dataSource.loadData(page, sortType, minePrice, maxPrice, cids, is_free_postage, is_preferential,is_custom_made,sort_newest,id,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
               if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setGoodsCount(editorRecommendBean.data.count)
                    view.setNewData(editorRecommendBean.data.products)
                    ++curPage
                } else {
                    view.showError(editorRecommendBean.status.message)
                }

            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载更多
     */
    fun loadMoreData(page: Int, sortType: String,minePrice: String, maxPrice: String,cids: String,is_free_postage: String, is_preferential: String, is_custom_made: String,sort_newest: String,id: String) {
        dataSource.loadData(page, sortType, minePrice, maxPrice, cids, is_free_postage, is_preferential,is_custom_made,sort_newest,id,object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    val products = editorRecommendBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                        ++curPage
                    }
                } else {
                    view.showError(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 默认条件加载更多
     */
    override fun loadMoreData() {
        loadMoreData(curPage, sortType,minePrice, maxPrice,cids,isFreePostage,isPreferential,isCustomMade,sortNewest,id)
    }



    /**
     * 获取商品分类
     */
    override fun getGoodsClassify(callBacks: IDataSource.HttpRequestCallBack) {
        dataSource.getGoodsClassify(object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                callBacks.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}