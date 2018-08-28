package com.thn.lexi.index.explore

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.index.bean.ProductBean
import com.thn.lexi.index.selection.GoodsData

class ExploreContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun goPage()
        fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {

        }

        fun addData(products: List<GoodsData.DataBean.ProductsBean>) {

        }

        fun loadMoreEnd(){}
        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun setGoodsClassData(categories: List<GoodsClassBean.DataBean.CategoriesBean>)
        fun setBannerData(banner_images: List<ExploreBannerBean.DataBean.BannerImagesBean>)
        fun setEditorRecommendData(products: List<ProductBean>)
        fun setFeatureNewGoodsData(products: List<ProductBean>)
        fun setBrandPavilionData(stores: List<BrandPavilionBean.DataBean.StoresBean>)

        fun setGoodsCollectionData(collections: List<GoodsCollectionBean.DataBean.CollectionsBean>)

        fun setGoodDesignData(products: List<ProductBean>)
        fun setGood100Data(products: List<ProductBean>)
        fun setBrandPavilionFocusStateData(b: Boolean, position: Int) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(cid: String, page: Int)

        fun loadMoreData(cid: String, page: Int)
    }
}