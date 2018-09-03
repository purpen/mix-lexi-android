package com.thn.lexi.index.detail

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.beans.BrandPavilionBean
import com.thn.lexi.beans.CouponBean

class GoodsDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setData(data: GoodsAllDetailBean.DataBean) {

        }

        fun setBrandPavilionData(data: BrandPavilionBean.DataBean?) {

        }

        fun setExpressData(expressInfoBean: ExpressInfoBean?) {

        }

        fun setSimilarGoodsData(data: BrandPavilionBean.DataBean?) {

        }

        fun setCouponData(coupons: List<CouponBean>) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(goodsId:String)

        fun getCouponsByStoreId(store_rid: String)

        fun loadBrandPavilionInfo(store_rid:String)

        fun getExpressTime(rid: String, store_rid: String, goodsId: String)

        fun getSimilarGoods(goodsId: String)
    }
}