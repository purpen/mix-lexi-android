package com.lexivip.lexi.publishShopWindow
import com.basemodule.tools.Util
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import java.io.IOException

class SelectGoodsPresenter(view: SelectGoodsContract.View) : SelectGoodsContract.Presenter {
    private var view: SelectGoodsContract.View = checkNotNull(view)
    private val dataSource: SelectGoodsModel by lazy { SelectGoodsModel() }
    private var page: Int = 1
    private var whichPage: Int = 0

    /**
     * 加载数据
     */
    override fun loadData(whichPage: Int) {
        this.whichPage = whichPage
        dataSource.loadData(whichPage, page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setNewData(editorRecommendBean.data.products)
                    page++
                } else {
                    view.showError(editorRecommendBean.status.message)
                }

            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(Util.getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 加载更多
     */
    override fun loadMoreData() {
        dataSource.loadData(whichPage, page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    val products = editorRecommendBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                        ++page
                    }
                } else {
                    view.showError(editorRecommendBean.status.message)
                }

            }

            override fun onFailure(e: IOException) {
                view.showError(Util.getString(R.string.text_net_error))
            }
        })
    }
}