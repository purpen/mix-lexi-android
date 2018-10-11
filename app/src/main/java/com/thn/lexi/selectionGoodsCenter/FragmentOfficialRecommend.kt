package com.thn.lexi.selectionGoodsCenter

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.BaseFragment
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.GoodsDetailActivity
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class FragmentOfficialRecommend:BaseFragment(),OfficialRecommendContract.View {
    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: OfficialRecommendPresenter

    private lateinit var adapter: AdapterHotGoods
    companion object {
        @JvmStatic
        fun newInstance(): FragmentOfficialRecommend = FragmentOfficialRecommend()
    }

    override fun initView() {
        presenter = OfficialRecommendPresenter(this)
        adapter = AdapterHotGoods(R.layout.adapter_hot_goods)

        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun setPresenter(presenter: OfficialRecommendContract.Presenter?) {
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

        adapter.setOnItemClickListener { _, _, position ->
            val productsBean = adapter.getItem(position)
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName,productsBean)
            startActivity(intent)
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