package com.lexivip.lexi.selectionGoodsCenter

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.beans.ProductBean

class AllGoodsContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(products: MutableList<ProductBean>) {

        }

        fun addData(products: List<ProductBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun setGoodsCount(count: Int) {

        }

    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh:Boolean)
        fun loadMoreData()
        fun loadData(page: Int, sortType: String, profitType: String, filterCondition: String, minePrice: String, maxPrice: String,cids:String)
        fun getGoodsClassify(param: IDataSource.HttpRequestCallBack)
    }
}