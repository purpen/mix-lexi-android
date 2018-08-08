package com.thn.lexi.goods.lifehouse

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.goods.explore.EditorRecommendBean

class LifeHouseContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setNewData(data: List<DistributionGoodsBean.DataBean.ProductsBean>) {

        }

        fun addData(products: List<DistributionGoodsBean.DataBean.ProductsBean>) {

        }

        fun loadMoreEnd(){}
        fun loadMoreComplete() {

        }

        fun setWelcomeInWeekData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {

        }

        fun setLookPeopleData(products: List<LookPeopleBean.DataBean.UsersBean>) {

        }

        fun setLifeHouseData(data: LifeHouseBean.DataBean) {

        }


    }

    interface Presenter : BasePresenter {
        fun loadData(cid: String, page: Int)

        fun loadMoreData(cid: String, page: Int)
    }
}