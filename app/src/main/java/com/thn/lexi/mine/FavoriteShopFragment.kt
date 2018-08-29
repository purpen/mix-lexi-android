package com.thn.lexi.mine
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import kotlinx.android.synthetic.main.fragment_favorite_shop.*

class FavoriteShopFragment : BaseFragment(), FavoriteDesignContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: FavoriteDesignPresenter
    private val adapter: AdapterDesignPavilion by lazy { AdapterDesignPavilion(R.layout.adapter_design_pavilion) }

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteShopFragment = FavoriteShopFragment()
    }

    override fun initView() {
        presenter = FavoriteDesignPresenter(this)
//        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
//        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        val view = View(activity)
        adapter.addHeaderView(view)
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(),R.color.color_f5f7f9,recyclerView))
//        adapter.emptyView =
    }

    override fun setPresenter(presenter: FavoriteDesignContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

//        swipeRefreshLayout.setOnRefreshListener {
//            adapter.setEnableLoadMore(false)
//            loadData()
//        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        },recyclerView)
    }



    override fun loadData() {
        presenter.loadData()
    }



    override fun setNewData(data: MutableList<DesignPavilionBean>) {
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
    }


    override fun addData(products: MutableList<DesignPavilionBean>) {
        adapter.addData(products)
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }


    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
//        swipeRefreshLayout.isRefreshing = false
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }
}