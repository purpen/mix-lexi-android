package com.lexivip.lexi.index.explore.goodsClassify

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.beans.ProductBean

class GoodsClassifyContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setNewData(data: List<ProductBean>) {
        }

        fun addData(products: MutableList<ProductBean>) {

        }

        fun loadMoreEnd() {}
        fun loadMoreComplete() {}
        fun loadMoreFail() {}
        fun setGoodsCount(count: Int) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh: Boolean,id:String)
        fun loadData(page: Int, sortType: String,minePrice: String, maxPrice: String,cids:String, is_free_postage: String, is_preferential: String,is_custom_made: String,sort_newest: String,id:String)
        fun loadMoreData()
        fun getGoodsClassify(callBacks: IDataSource.HttpRequestCallBack)
    }
}