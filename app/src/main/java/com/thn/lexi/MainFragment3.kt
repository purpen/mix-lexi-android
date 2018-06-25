package com.thn.lexi

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.basemodule.tools.Constants
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.goods.GoodsData
import com.thn.lexi.mine.MineContract
import com.thn.lexi.mine.MineFavoritesAdapter
import com.thn.lexi.mine.MinePresenter
import kotlinx.android.synthetic.main.fragment_main3.*

class MainFragment3 : BaseFragment(), MineContract.View {
    private val dialog: WaitingDialog? by lazy { WaitingDialog(activity) }
    private lateinit var presenter: MinePresenter
    private var page: Int = 1
    private lateinit var adapter0: MineFavoritesAdapter

    companion object {
        fun newInstance(): MainFragment3 {
            return MainFragment3()
        }
    }

    override val layout: Int = R.layout.fragment_main3

    override fun initView() {
        this.presenter = MinePresenter(this)
        adapter0 = MineFavoritesAdapter(R.layout.layout_goods_adapter)

        val headView = LayoutInflater.from(activity).inflate(R.layout.view_mine_head, null)

        adapter0.addHeaderView(headView)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView0.setHasFixedSize(true)
        recyclerView0.layoutManager = linearLayoutManager
        recyclerView0.adapter = adapter0
        recyclerView0.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.VERTICAL, resources.getDimensionPixelSize(R.dimen.dp10), resources.getColor(R.color.color_d1d1d1)))
    }


    override fun setPresenter(presenter: MineContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {
        swipeRefreshLayout.setOnRefreshListener {
            adapter0.setEnableLoadMore(false)
            loadData()
        }

        adapter0.setOnLoadMoreListener({
            presenter.loadMoreData("", page)
        }, recyclerView0)
    }

    override fun loadData() {
        page = 1
        presenter.loadData("", page)
    }

    override fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter0.setNewData(data)
        adapter0.setEnableLoadMore(true)
        showEndView()
        ++page
    }

    override fun addData(products: List<GoodsData.DataBean.ProductsBean>) {
        adapter0.addData(products)
        ++page
        showEndView()
    }

    private fun showEndView() {
        if (adapter0.data.size < Integer.valueOf(Constants.PAGE_SIZE)) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter0.loadMoreEnd(false)

        } else {
            adapter0.loadMoreComplete()
        }
    }


    override fun showLoadingView() {
        if (!swipeRefreshLayout.isRefreshing) dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(string: String) {
        swipeRefreshLayout.isRefreshing = false
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

}