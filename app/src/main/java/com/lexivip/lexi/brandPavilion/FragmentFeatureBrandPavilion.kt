package com.lexivip.lexi.brandPavilion
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.index.explore.BrandPavilionListBean
import kotlinx.android.synthetic.main.fragment_swipe_refresh_recyclerview.*

class FragmentFeatureBrandPavilion : BaseFragment(), FeatrueBrandPavilionContract.View {
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
        loadingView.visibility = View.VISIBLE
        loadingView.setOffsetTop(DimenUtil.dp2px(90.0))
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


        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2BrandPavilionActivity(item.rid)
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
        loadingView.show()
    }

    override fun dismissLoadingView() {
        loadingView.dismiss()
    }

    override fun showError(string: String) {
        adapter.loadMoreFail()
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

}