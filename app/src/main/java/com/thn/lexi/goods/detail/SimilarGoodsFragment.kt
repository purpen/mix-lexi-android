package com.thn.lexi.goods.detail

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.basemodule.ui.BaseFragment
import com.thn.lexi.R
import kotlinx.android.synthetic.main.fragment_similar_goods.*

class SimilarGoodsFragment :BaseFragment(),SimilarGoodsContract.View{
    private lateinit var presenter:SimilarGoodsPresenter
    override val layout: Int = R.layout.fragment_similar_goods
    private lateinit var adapter: SimilarGoodsAdapter
    private var page: Int = 1
    companion object {
        @JvmStatic
        fun newInstance(): SimilarGoodsFragment = SimilarGoodsFragment()
    }

    override fun initView() {
        presenter = SimilarGoodsPresenter(this)
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(activity,2)
        recyclerView.layoutManager = gridLayoutManager
        adapter = SimilarGoodsAdapter(R.layout.adapter_similar_goods)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        val pixelSize15 = resources.getDimensionPixelSize(R.dimen.dp15)
        val pixelSize5 = resources.getDimensionPixelSize(R.dimen.dp5)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position%2==0){
                    outRect.set(pixelSize15,pixelSize15,pixelSize5,0)
                }else{
                    outRect.set(pixelSize5,pixelSize15,pixelSize15,0)
                }
            }
        })
    }

    override fun setPresenter(presenter: SimilarGoodsContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun loadData() {
        presenter.loadData(page)
    }

    override fun setNewData(products: List<SimilarGoodsBean.DataBean.ProductsBean>) {
        adapter.setNewData(products)
        adapter.setEnableLoadMore(true)
        ++page
    }


    override fun addData(products: List<SimilarGoodsBean.DataBean.ProductsBean>) {
        adapter.addData(products)
        ++page
    }

    override fun installListener() {
        adapter.setOnLoadMoreListener({
            presenter.loadMoreData(page)
        },recyclerView)
    }

    override fun showError(string: String) {

    }

    override fun goPage() {

    }


    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }
}