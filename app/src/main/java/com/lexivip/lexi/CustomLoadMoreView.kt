package com.lexivip.lexi
import com.chad.library.adapter.base.loadmore.LoadMoreView

class CustomLoadMoreView: LoadMoreView() {

    override fun getLayoutId(): Int = R.layout.view_custome_load_more


    override fun getLoadingViewId(): Int {
        return R.id.imageViewLoading
    }

    override fun getLoadFailViewId(): Int {
       return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return 0
    }
}