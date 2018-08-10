package com.thn.lexi.goods.lifehouse

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.goods.explore.EditorRecommendBean
import com.thn.lexi.user.completeinfo.UploadTokenBean
import org.json.JSONArray

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

        fun setLookPeopleData(users: List<LookPeopleBean.DataBean.UsersBean>) {

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

        fun setUploadTokenData(uploadTokenBean: UploadTokenBean?, byteArray: ByteArray) {

        }

        fun setLifeHouseLogoData(ids: JSONArray) {

        }


    }

    interface Presenter : BasePresenter {
        fun loadData(cid: String, page: Int)

        fun loadMoreData(cid: String, page: Int)
    }
}