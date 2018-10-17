package com.lexivip.lexi.index.explore.collection
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.index.explore.GoodsCollectionBean
import kotlinx.android.synthetic.main.acticity_header_recyclerview.*


class CollectionListActivity : BaseActivity(), CollectionListContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: CollectionListPresenter by lazy { CollectionListPresenter(this) }

    private val adapter: AdapterCollectionList by lazy { AdapterCollectionList(R.layout.adapter_collection_list) }

    override val layout: Int = R.layout.acticity_header_recyclerview

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_collection)
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 15f))
    }

    override fun setPresenter(presenter: CollectionListContract.Presenter?) {
        setPresenter(presenter)
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


        //跳转集合详情
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) as GoodsCollectionBean.DataBean.CollectionsBean
            val intent = Intent(this, CollectionDetailActivity::class.java)
            intent.putExtra(CollectionDetailActivity::class.java.simpleName, item.id)
            startActivity(intent)
        }
    }


    override fun setNewData(collections: MutableList<GoodsCollectionBean.DataBean.CollectionsBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(collections)
    }

    override fun addData(collections: MutableList<GoodsCollectionBean.DataBean.CollectionsBean>) {
        adapter.addData(collections)
        adapter.setEnableLoadMore(true)
    }

    override fun requestNet() {
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
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

}
