package com.lexivip.lexi.index.discover

import android.support.annotation.NonNull
import android.view.View
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.LifeWillBean
import com.lexivip.lexi.beans.ProductBean
import org.json.JSONObject


class ArticleDetailContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setData(data: JSONObject) {

        }

        fun setFocusState(followed_status: Int) {

        }

        fun setRelateStoriesData(data: MutableList<LifeWillBean>) {

        }

        fun setRecommendProductsData(products: List<ProductBean>) {

        }

        fun setBrandPavilionFocusState(favorite: Boolean) {

        }
    }

    interface Presenter : BasePresenter {
        fun loadData(isRefresh: Boolean, rid: String)
        fun focusUser(uid: String, v: android.view.View, isFollow: Boolean)
        fun getRelateStories(rid: String)
        fun getRecommendProducts(rid: String)
        fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, v: android.view.View)
    }
}