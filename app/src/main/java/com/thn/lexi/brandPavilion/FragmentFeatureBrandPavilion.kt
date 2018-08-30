package com.thn.lexi.brandPavilion

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import com.thn.lexi.discoverLifeAesthetics.ShowWindowBean
import com.thn.lexi.discoverLifeAesthetics.ShowWindowDetailActivity
import com.thn.lexi.index.explore.BrandPavilionListBean
import kotlinx.android.synthetic.main.fragment_swipe_refresh_recyclerview.*

class FragmentFeatureBrandPavilion : BaseFragment(), FeatrueBrandPavilionContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_swipe_refresh_recyclerview
    private val presenter: FeatureBrandPavilionPresenter by lazy { FeatureBrandPavilionPresenter(this) }
    private val adapter: AdapterFeatureBrandPavilion by lazy { AdapterFeatureBrandPavilion(R.layout.adapter_feature_brand_pavilion) }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentFeatureBrandPavilion = FragmentFeatureBrandPavilion()
    }

    override fun setPresenter(presenter: FeatrueBrandPavilionContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(),R.color.color_f5f7f9, recyclerView))
    }

    override fun installListener() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)


        adapter.setOnItemChildClickListener { adapter, view, position ->
//            val showWindowBean = adapter.getItem(position) as ShowWindowBean.DataBean.ShopWindowsBean
            when (view.id) {

            }
        }

        adapter.setOnItemClickListener { adapter, view, position ->
            //
            ToastUtil.showInfo("品牌馆主页")
//            val showWindowBean = adapter.getItem(position) as ShowWindowBean.DataBean.ShopWindowsBean
//            val intent = Intent(context, ShowWindowDetailActivity::class.java)
//            intent.putExtra(ShowWindowDetailActivity::class.java.simpleName, showWindowBean)
//            startActivity(intent)
        }
    }


    override fun setNewData(stores: MutableList<BrandPavilionListBean.DataBean.StoresBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(stores)
    }

    override fun addData(stores: MutableList<BrandPavilionListBean.DataBean.StoresBean>) {
        adapter.addData(stores)
        adapter.setEnableLoadMore(true)
    }

    override fun loadData() {
        presenter.loadData(false)
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadMoreFail() {
        adapter.loadMoreFail()
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
        adapter.loadMoreFail()
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

}