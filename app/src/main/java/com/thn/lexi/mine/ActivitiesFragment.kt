package com.thn.lexi.mine
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.Constants
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.goods.selection.SelectionContract
import com.thn.lexi.goods.selection.SelectionPresenter
import com.thn.lexi.goods.selection.GoodsAdapter
import com.thn.lexi.goods.selection.GoodsData
import kotlinx.android.synthetic.main.fragment_activities.*

class ActivitiesFragment : BaseFragment(), SelectionContract.View {
    private val dialog: WaitingDialog? by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_activities
    private lateinit var presenter: SelectionPresenter
    private var page: Int = 1
    private lateinit var adapter: GoodsAdapter

    companion object {
        @JvmStatic
        fun newInstance(): ActivitiesFragment = ActivitiesFragment()
    }

    override fun initView() {
        presenter = SelectionPresenter(this)
        adapter = GoodsAdapter(R.layout.adapter_goods_layout)

        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(),LinearLayoutManager.VERTICAL,resources.getDimensionPixelSize(R.dimen.dp10),Util.getColor(R.color.color_d1d1d1)))
    }

    override fun setPresenter(presenter: SelectionContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

        swipeRefreshLayout.setOnRefreshListener {
            adapter.setEnableLoadMore(false)
            loadData()
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData("",page)
        },recyclerView)
    }



    override fun loadData() {
        page = 1
        presenter.loadData("", page)
    }

    override fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
        showEndView()
        ++page
    }

    override fun addData(products: List<GoodsData.DataBean.ProductsBean>) {
        adapter.addData(products)
        ++page
        showEndView()
    }

    private fun showEndView() {
        if (adapter.data.size < Integer.valueOf(Constants.PAGE_SIZE)) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(false)

        } else {
            adapter.loadMoreComplete()
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