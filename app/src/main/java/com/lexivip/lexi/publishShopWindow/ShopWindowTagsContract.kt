package com.lexivip.lexi.publishShopWindow
import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.search.FuzzyWordMatchListBean
import com.lexivip.lexi.search.HotSearchBean

class ShopWindowTagsContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setNewData(data: List<ProductBean>) {
        }

        fun addData(products: MutableList<ProductBean>) {

        }

        fun setHotSearchData(search_items: List<HotSearchBean.DataBean.SearchItemsBean>) {

        }

        fun setFuzzyWordListData(search_items: List<FuzzyMatchTagsBean.DataBean.KeywordsBean>) {

        }

        fun setHotTags(keywords: List<HotShopWindowTagsBean.DataBean.KeywordsBean>) {

        }

        fun loadMoreEnd() {
            
        }

        fun loadMoreComplete() {
            
        }

        fun setMoreFuzzyWordListData(keywords: List<FuzzyMatchTagsBean.DataBean.KeywordsBean>) {

        }

        fun setAddTagSuccess(searchString: String) {

        }
    }

    interface Presenter : BasePresenter {
        fun getHotSearch()
        fun getFuzzyWordList(keyWord: String)
        fun getMoreFuzzyWordList()
        fun addShopWindowTag(searchString: String)
    }
}