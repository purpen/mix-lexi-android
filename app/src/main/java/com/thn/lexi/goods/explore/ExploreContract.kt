package com.thn.lexi.goods.explore

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.thn.lexi.goods.selection.GoodsData

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
        fun setEditorRecommendData(products: List<EditorRecommendBean.DataBean.ProductsBean>)
        fun setFeatureNewGoodsData(products: List<EditorRecommendBean.DataBean.ProductsBean>)
        fun setBrandPavilionData(stores: List<BrandPavilionBean.DataBean.StoresBean>)

        fun setGoodsCollectionData(collections: List<GoodsCollectionBean.DataBean.CollectionsBean>)

        fun setGoodDesignData(products: List<EditorRecommendBean.DataBean.ProductsBean>)
        fun setGood100Data(products: List<EditorRecommendBean.DataBean.ProductsBean>)
    }

    interface Presenter : BasePresenter {
        fun loadData(cid: String, page: Int)

        fun loadMoreData(cid: String, page: Int)
    }
}