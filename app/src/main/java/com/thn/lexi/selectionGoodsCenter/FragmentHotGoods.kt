package com.thn.lexi.selectionGoodsCenter

import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.BaseFragment
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class FragmentHotGoods:BaseFragment(),HotGoodsContract.View {
    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: HotGoodsPresenter

    private lateinit var adapter: AdapterHotGoods
    companion object {
        @JvmStatic
        fun newInstance(): FragmentHotGoods = FragmentHotGoods()
    }

    override fun initView() {
        presenter = HotGoodsPresenter(this)
        adapter = AdapterHotGoods(R.layout.adapter_hot_goods)

        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun setPresenter(presenter: HotGoodsContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun loadData() {
        presenter.loadData()
    }

    override fun setNewData(products: MutableList<ProductBean>) {
        adapter.setNewData(products)
    }

    override fun addData(products: List<ProductBean>) {
        adapter.addData(products)
        adapter.notifyDataSetChanged()
    }

    override fun installListener() {
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val productsBean = adapter.getItem(position) as ProductBean
            when (view.id) {
                R.id.textView4 -> ToastUtil.showInfo("卖")
                R.id.textView5 -> ToastUtil.showInfo("上架")
            }
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)
    }

    override fun showLoadingView() {

    }

    override fun dismissLoadingView() {

    }

    override fun showError(string: String) {

    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun goPage() {

    }
}