package com.lexivip.lexi.index.lifehouse

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean
import org.json.JSONArray

class LifeHouseContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setNewData(data: List<ProductBean>) {

        }

        fun addData(products: List<ProductBean>) {

        }

        fun loadMoreEnd(){}
        fun loadMoreComplete() {

        }

        fun setWelcomeInWeekData(products: List<ProductBean>) {

        }

        fun setLookPeopleData(data: LookPeopleBean.DataBean) {

        }

        fun setLifeHouseData(data: LifeHouseBean.DataBean) {

        }

        fun showInfo(s: String) {

        }

        fun deleteDistributeGoods(position: Int) {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun setEditLifeHouseData(bean: LifeHouseBean) {

        }

        fun setLifeHouseLogoData(ids: JSONArray) {

        }

        fun setNewPublishProductsData(products: List<ProductBean>) {

        }

        fun setNewProductsExpressData(products: List<ProductBean>) {

        }


    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh:Boolean)

        fun loadMoreData()

        fun getNewPublishProducts(isRefresh: Boolean)
    }
}